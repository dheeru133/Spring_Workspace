# You can write your azure powershell scripts inline here. 
# You can also pass predefined and custom variables to this script using arguments
param(
    [Parameter(Mandatory=$true)][string]$SubScriptionName, #Azure Subscription Name "org-env-nonprod"
    [Parameter(Mandatory=$true)][string]$ResourceGroupName, #Azure Resource Group Name "org-env-qa"
    [Parameter(Mandatory=$false)][string]$Location ="use2", #Resource Location 
    [Parameter(Mandatory=$true)][string]$DataFactoryName, #Azure Data Factory Name "org-env-qa-01"
    [Parameter(Mandatory=$true)][string]$InputFolderPath, #
    [Parameter(Mandatory=$true)][string]$PipelineReleaseList, #
	[Parameter(Mandatory=$true)][string]$PipelinePurgeList, #
    [Parameter(Mandatory=$true)][string]$DataSetReleaseList #
)
#$env:SYSTEM_ARTIFACTSDIRECTORY
$InputFolderPath="$InputFolderPath"
Write-Output "Script is excuting in $($InputFolderPath)"

Select-AzureRmSubscription -SubscriptionName $SubScriptionName

#Reference Data Factory
$dataFactory = Get-AzureRmDataFactoryV2 -ResourceGroupName $ResourceGroupName  -Name $DataFactoryName 
#$InputFolderPath = $Build.SourcesDirectory
#$InputFolderPath = $PWD

#Reference datasets Services
$files = Get-ChildItem $InputFolderPath\dataset\ -Recurse -Include *.json -ErrorAction Stop
foreach ($file in $files)  
{
    Write-Output "Creating dataset from $($file.FullName)"
    $json = Get-Content $file.FullName -Raw -ErrorAction Stop
    $svc = $json | ConvertFrom-Json
    $svcName = $svc.name
    Set-AzureRmDataFactoryV2Dataset -ResourceGroupName $ResourceGroupName -DataFactoryName $DataFactoryName -Name $svcName -DefinitionFile $file.FullName -Force -ErrorAction Stop 
}
#$oldPipeLines = Get-AzureRmDataFactoryV2Pipeline -ResourceGroupName $ResourceGroupName -DataFactoryName $DataFactoryName
#foreach($oldPipeLine in $oldPipeLines){
#    Write-Output    "Removing Old Pipeline $($oldPipeLine.Name)" 
#    Remove-AzureRmDataFactoryV2Pipeline -ResourceGroupName  $ResourceGroupName -Name $oldPipeLine.Name -DataFactoryName $DataFactoryName -Force -ErrorAction Stop
#    Write-Output    "Removed Old Pipeline $($oldPipeLine.Name)"
#}
#Reference pipelines Services
#Reference pipelines Services
$pipeLines = Get-Content $InputFolderPath\$PipelinePurgeList
foreach($pipeline in $pipeLines)
{   
	IF ($pipeline.Substring(0,1) -ne '#')
	{
		
		Write-Output "Deleting pipeline $($pipeline)"
		Remove-AzureRmDataFactoryV2Pipeline -ResourceGroupName $ResourceGroupName -DataFactoryName $DataFactoryName -Name $pipeline -Force -ErrorAction Stop 
	}
	ELSE
	{
		Write-Output "Commented out pipeline $($pipeline)"
	}
}


$pipeLines = Get-Content $InputFolderPath\$PipelineReleaseList
foreach($pipeline in $pipeLines)
{   
	IF ($pipeline.Substring(0,1) -ne '#')
	{
		$fqfn = "$InputFolderPath\pipeline\$pipeline"
		Write-Output "Creating pipeline from $($pipeline)"
		$json = Get-Content $fqfn -Raw -ErrorAction Stop
		$svc = $json | ConvertFrom-Json
		$svcName = $svc.name
		Write-Output "Folder $($svc.properties.folder.name)"
		Set-AzureRmDataFactoryV2Pipeline -ResourceGroupName $ResourceGroupName -DataFactoryName $DataFactoryName -Name $svcName -DefinitionFile $fqfn -Force -ErrorAction Stop 
	}
	ELSE
	{
		Write-Output "Commented out pipeline $($pipeline)"
	}
}
