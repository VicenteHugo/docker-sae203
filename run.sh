#-------------------------------------------------------------------------------#
# Liste les fichiers pour chaque dossiers et sous dossier du dossiers d'origine #
#-------------------------------------------------------------------------------#
listerDossiers()
{
    for i in `ls -l $1 | tr -s ' ' | cut -d ' ' -f 9`
    do
        if [ -f $1"/"$i ]                                       
        then
            ajoutFichiers $1 $2 $3 $i              
        fi

        if [ -d $1"/"$i ]               
        then
            source=$1"/"$i                          
            listerDossiers $source $2 $3           
        fi
    done
}

#---------------------------------------------------------------#
# Ajoute le fichier à la compile list si l'extension correspond #
#---------------------------------------------------------------#
ajoutFichiers()
{
    extension=${4##*.}          

    if [ "$extension" == "$2" ]   
    then
        echo $1"/"$4 >> $3         
    fi
}

#-----------------------------------------#
# Vérifie si le dossier source est valide #
#-----------------------------------------#
verificationDossier()
{
    # source="$1"          
    # until [ -d $source ]  
    # do
    #     read -p "Veuillez entrez le dossier racine à partir du quel la compile.list vas être générer : " source
    # done

    # if [ -z $source ]   
    # then 
        source="."
    # fi


    > ./compile.list   
    listerDossiers $source $extensionValide $nomFichierSortie  
}

#------#
# Main #
#------#
extensionValide="java"              
nomFichierSortie="./compile.list"  

if [ $# -gt 0 ]                    
then
    source="$1"                    

    verificationDossier $1         
else
    verificationDossier "rien"   
fi

javac -encoding utf8 -d "./bin" "@compile.list"

cd ./bin

java controleur.Controleur -cp "./bin"
