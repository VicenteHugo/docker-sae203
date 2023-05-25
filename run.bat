@echo off

:: Création de la compile list
call :genererCompileList ".\src"

:: Création des dossiers bin et donnees s'ils n'existent pas ::
IF NOT EXIST ".\bin\"         ( mkdir ".\bin\" )
IF NOT EXIST ".\bin\donnees\" ( mkdir ".\bin\donnees\" )

XCOPY ".\donnees" ".\bin\donnees" /E /Y >NUL

echo Compilation...
call javac -encoding utf8 -d ".\bin" "@compile.list" && ( echo Lancement du programme... & call java -cp "%CLASSPATH%;.\bin" controleur.Controleur && echo Fin de l'execution. || ( echo. & echo Erreur d'EXECUTION. )) || echo Erreur de COMPILATION.

goto :eof


:genererCompileList
    SETLOCAL ENABLEDELAYEDEXPANSION
        SET "nomFichierChoixDossier=choixDossier.vbs"

        IF EXIST "%~1" (
            SET "source=%~1"
            SET "extensionValide=java"
            SET "nomFichierSortie=.\compile.list"

            :: Suppression du fichier de choix de dossier si il existe ::
            IF EXIST "%nomFichierChoixDossier%" del "%nomFichierChoixDossier%"

            echo.> .\compile.list

            call :listerDossiers
            call :ListerFichiers
        ) else (
            

            echo %nomFichierChoixDossier%
            echo dossier = inputbox ^("Veuillez entrez le dossier racine à partir du quel la compile.list vas être générer"^, "Séléctionner un dossier"^) > %nomFichierChoixDossier%
            echo if dossier ^<^> "" then >> %nomFichierChoixDossier%
            echo   prog = "generateur_compile-list.bat """ ^& dossier ^& """" >> %nomFichierChoixDossier%
            echo   WScript.CreateObject ^("Wscript.shell"^).Run^(prog^), ^0 >> %nomFichierChoixDossier%
            echo end if >> %nomFichierChoixDossier%

            start %nomFichierChoixDossier%
        )
    ENDLOCAL
goto :eof


::-------------------------------------------------------------------------------::
:: Liste les fichiers pour chaque dossiers et sous dossier du dossiers d'origine ::
::-------------------------------------------------------------------------------::
:listerDossiers
    FOR /f %%i IN ('dir "!source!" /b /ad') DO (
        dir "!source!" /b /a-d 2>nul >nul && ( call :ListerFichiers )

        set "source=%source%\%%i"

        call :listerDossiers
    )
goto :eof



::-----------------------------------------------------------::
:: Liste les fichiers pour le dossiersen cours nommer source ::
::-----------------------------------------------------------::
:ListerFichiers
    :: liste les fichiers dans le dossier courant ::
    FOR /f %%i IN ('dir "!source!" /b /a-d') DO (
        set "extension=%%~xi"
        IF "%%~xi" == ".%extensionValide%" ( echo !source!\%%i >> %nomFichierSortie% )
    )

    IF "!extension!" == ".%extensionValide%" ( echo.>> %nomFichierSortie% )
goto :eof
