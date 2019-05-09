<#
Dheeraj chaudhary
    This script can be used to expand a U-SQL project build output(.usqlpack file).usqlpack and upload U-SQL files to ADLS.

    Example :
        Deploy.ps1 -ADLSName "myadlsaccount" -ArtifactsRoot "C:\USQLProject\bin\debug\"
#>

param(
    [Parameter(Mandatory=$true)][string]$ADLSName, # ADLS account name to upload U-SQL scripts
    [Parameter(Mandatory=$true)][string]$ArtifactsRoot, # Root folder of U-SQL project build output
    [Parameter(Mandatory=$false)][string]$DesitinationFolder # Desitination folder in ADLS
)

Function UploadResources()
{
    Write-Host "************************************************************************"
    Write-Host "Uploading files to $ADLSName"
    Write-Host "***********************************************************************"

    $usqlScripts = GetUsqlFiles

        Write-Host "Uploading file: " $usqlScripts  $usqlScripts
        Import-AzureRmDataLakeStoreItem -AccountName $ADLSName -Path $usqlScripts\ -Destination $DesitinationFolder -Recurse -Force -ErrorAction Stop

}

function Unzip($USQLPackfile, $UnzipOutput)
{
    $USQLPackfileZip = Rename-Item -Path $USQLPackfile -NewName $([System.IO.Path]::ChangeExtension($USQLPackfile, ".zip")) -Force -PassThru
    Expand-Archive -Path $USQLPackfileZip -DestinationPath $UnzipOutput -Force
    Rename-Item -Path $USQLPackfileZip -NewName $([System.IO.Path]::ChangeExtension($USQLPackfileZip, ".usqlpack")) -Force
	Write-Host "************************************************************************"
	Write-Host " Unzip Completed for USQL Files " $USQLPackfile, $UnzipOutput
	Write-Host "************************************************************************"
}

Function GetUsqlFiles()
{
	Write-Host "************************************************************************"
	Write-Host " Getting USQL Files "
	Write-Host "************************************************************************"
    $USQLPackfiles = Get-ChildItem -Path $ArtifactsRoot -Include *.usqlpack -File -Recurse -ErrorAction Stop

    $UnzipOutput = Join-Path $ArtifactsRoot -ChildPath "UnzipUSQLScripts"
	Write-Host " USQL Filepack :: " $USQLPackfiles
    foreach ($USQLPackfile in $USQLPackfiles)
    {
		Write-Host " Unzipping USQL File " $USQLPackfile
        Unzip $USQLPackfile $UnzipOutput
    }

    return $UnzipOutput
}

UploadResources