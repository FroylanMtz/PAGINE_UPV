# coding: utf-8
import nltk
from   nltk.tag.stanford import StanfordNERTagger
import json
import os

#nltk.download('punkt')

def cargarTuits(archivo):
    tuits = []
    contenido_del_archivo = open(archivo, "r")
    lineas = contenido_del_archivo.readlines()

    for linea in lineas:
        tuit = json.loads(linea)
        tuits.append( tuit["Tweet"] )

    return tuits

def cargarNombresArchivos(directorio):
    archivos = os.listdir(directorio)
    return archivos

def main():

    jar        = './stanford-ner.jar'
    model      = './spanish.kbp.ancora.distsim.s512.crf.ser.gz'
    ner_tagger = StanfordNERTagger(model, jar, encoding='utf8')
    documento  = open("es-train-tuits.pos", "a+", encoding="utf8")

    i = 0
    carpetas = cargarNombresArchivos("tuits_limpios/")
    for carpeta in carpetas:
        print("Carpeta: " + carpeta)
        archivos = cargarNombresArchivos("tuits_limpios/" + carpeta)
        for archivo in archivos:
            print("Archivo " + archivo)
            tuits = cargarTuits("tuits_limpios/" + carpeta + "/" +archivo)
            token_tuit = []
            etiquetas = []

            for tuit in tuits:
                token_tuit = nltk.word_tokenize(tuit)
                etiquetas = ner_tagger.tag(token_tuit)
                oracion = ""
                hayPersona = False

                for j in range( len(etiquetas) ):

                    if( etiquetas[j][1] == "PERS" ):
                        oracion = oracion + etiquetas[j][0] + " "
                        hayPersona = True

                if(hayPersona):
                    i = i + 1
                    documento.write(oracion+"\n")
                    print("Tuit #" + str(i) + " " + oracion)

    
    documento.close()
    
main()