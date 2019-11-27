# coding: utf-8
import nltk
from   nltk.tag.stanford import StanfordNERTagger
import json
import os

#nltk.download('punkt')

def cargarTuits(archivo):
    tuits = []
    contenido_del_archivo = open("tuits_para_etiquetar/" + archivo, "r")
    lineas = contenido_del_archivo.readlines()

    for linea in lineas:
        tuit = json.loads(linea)
        tuits.append( tuit["Tweet"] )

    return tuits


def cargarNombresArchivos(directorio):
    archivos = os.listdir(directorio)
    return archivos

def main():

    lista_archivos = cargarNombresArchivos("tuits_para_etiquetar/")

    jar = './stanford-ner.jar'
    model = './spanish.kbp.ancora.distsim.s512.crf.ser.gz'

    # Prepare NER tagger with english model
    ner_tagger = StanfordNERTagger(model, jar, encoding='utf8')
    archivo = open("es-train-tuits.pos", "a+", encoding="utf8")

    i = 0
    #for a in lista_archivos:
    #    print("Procesando... " + a)

    tuits = cargarTuits("presidente_2019_10_18_05_00_54.json")
    token_tuit = []
    etiquetas = []

    for tuit in tuits:
        token_tuit = nltk.word_tokenize(tuit)
        etiquetas = ner_tagger.tag(token_tuit)

        oracion = ""
        hayPersona = False

        for j in range( len(etiquetas) ):

            if(etiquetas[j][1] == "PERS" and ( j == 0 or j == len(etiquetas))):
                oracion = "<START:person> "
                hayPersona = True
            
            if(etiquetas[j][1] == "ORG" and ( j == 0 or j == len(etiquetas))):
                oracion = "<START:person> "
                hayPersona = True

            oracion = oracion + etiquetas[j][0] + " "

            if( j < len(etiquetas) - 1 ):
                if( etiquetas[j][1] != "PERS" and etiquetas[j+1][1] == "PERS"):
                    oracion = oracion + "<START:person> "
                    hayPersona = True
                
                if( etiquetas[j][1] == "PERS" and etiquetas[j+1][1] != "PERS"):
                    oracion = oracion + "<END> "
                    hayPersona = True

            if( j < len(etiquetas) - 1 ):
                if( etiquetas[j][1] != "ORG" and etiquetas[j+1][1] == "ORG"):
                    oracion = oracion + "<START:person> "
                    hayPersona = True
                
                if( etiquetas[j][1] == "ORG" and etiquetas[j+1][1] != "ORG"):
                    oracion = oracion + "<END> "
                    hayPersona = True

            if(etiquetas[j][1] == "PERS" and j == len(etiquetas)-1 ):
                oracion = oracion + " <END>"
                hayPersona = True

            if(etiquetas[j][1] == "ORG" and j == len(etiquetas)-1 ):
                oracion = oracion + " <END>"
                hayPersona = True

        if(hayPersona):
            i = i + 1
            archivo.write(oracion+"\n")
            print("Tuit #" + str(i) )

    archivo.close()
    
main()