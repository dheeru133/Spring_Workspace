# You can write your azure powershell scripts inline here. 
# You can also pass predefined and custom variables to this script using arguments
param(
    [Parameter(Mandatory=$true)][string]$aasServer, #Azure Subscription Name "org-env"
    [Parameter(Mandatory=$true)][string]$modelName, #Azure Resource Group Name "org-env-qa"
    [Parameter(Mandatory=$true)][string]$adminName, #Resource Location 
    [Parameter(Mandatory=$true)][string]$adminPassword, #Azure Data Factory Name "org-env-qa"
    [Parameter(Mandatory=$true)][string]$pathToModel ,
	[Parameter(Mandatory=$true)][string]$connectionType, #

    [Parameter(Mandatory=$true)][string]$sourceSQLServer, #
    [Parameter(Mandatory=$true)][string]$sourceSQLDatabase, #
	[Parameter(Mandatory=$true)][string]$sourceSQLUsername, #
    [Parameter(Mandatory=$true)][string]$sourceSQLPassword, #

	[Parameter(Mandatory=$true)][string]$overwrite, #
    [Parameter(Mandatory=$true)][string]$remove #
)

# Installs SQL Module
Install-PackageProvider -Name NuGet -Force -Scope CurrentUser
#Install-Module -Name SqlServer -Force -Verbose -Scope CurrentUser

Write-Host "installing modules: start"
Install-Module -Name SqlServer -Force -AllowClobber
Write-Host "installing modules: end"

Write-Host "getting modules list: start"
Get-Module SqlServer -ListAvailable
Write-Host "getting modules list: end"

Write-Host "listing commands: start"
Import-Module -Name SqlServer
Get-Command -Module SqlServer
Write-Host "listing commands: end"

# Import the logic of the linked module
Import-Module $PSScriptRoot\deploy-aas-db.psm1 -Force
#Import-Module $PSScriptRoot\ps_modules\VstsAzureHelpers_
#Import-Module $PSScriptRoot\ps_modules\SqlServer


#$env:SYSTEM_ARTIFACTSDIRECTORY
$InputFolderPath="$InputFolderPath"
Write-Output "Script is excuting in $($InputFolderPath)"


# This is a hack since the agent passes this as a string.
if ($overwrite -eq "true") {
    $overwrite = $true
} else {
    $overwrite = $false
}

if ($remove -eq "true") {
    $remove = $true
} else {
    $remove = $false
}

# Read .asdatabase/.bim file as model
$model = ReadModel -ModelFile $pathToModel

Write-Output "AAS Model   $($model)"
# Remove old model
if ($remove) {
#	$result = RemoveModel -Server $aasServer -ModelName $modelName
	$result = $true;
} else {
    $result = $true;
}

# Rename model name
$model = RenameModel -Model $model -NewName $modelName

# Remove security Ids
$model = RemoveSecurityIds -Model $model

# Alter model JSON with provided username/password
switch ($connectionType) {
    "sql" {
        $model = ApplySQLSecurity -Model $model -Server $sourceSQLServer -Database $sourceSQLDatabase -UserName $sourceSQLUsername -Password $sourceSQLPassword
    }
}

# Create TSML command
$tsmlCommand = PrepareCommand -Model $model -Overwrite $overwrite -ModelName $modelName

Write-Output "AAS Command   $($tsmlCommand)"
$SecureAdminPassword = ConvertTo-SecureString ($adminPassword) -AsPlainText -Force
# Deploy new model
if ($result) {
    $result = DeployModel -Server $aasServer -Command $tsmlCommand -Admin $adminName -Password $SecureAdminPassword
}

Write-Output "AAS result   $($result)"

switch ($result) {
    0 {
        Write-Output "Deploy database to '$aasServer' complete"
    }
    1 {
        Write-Output "Deploy database to '$aasServer' complete with warnings"
    }
    -1 {
        Write-Error "Deploy database to '$aasServer' complete with errors"
		Write-Output "Deploy database to '$aasServer' complete with errors"
        throw
    }
}