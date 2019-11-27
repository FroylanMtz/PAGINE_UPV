import pandas
import json
import nltk
from nltk.tag import StanfordPOSTagger
from nltk.tokenize import TweetTokenizer


etiquetas_tuits = []
tokens_tuits = []

def cargarTuits():
    tuits = []

    contenido_del_archivo = open("Tuits/tuits2.json", "r", encoding="utf8")
    lineas = contenido_del_archivo.readlines()

    for linea in lineas:
        tuit = json.loads(linea)
        tuits.append( tuit["Tweet"] )

    return tuits

def etiquetarPalabras():
    tuits = cargarTuits()
    tokenizer = TweetTokenizer(strip_handles=True, reduce_len=True)
    archivo = open("es-train-tuits2.pos", "a+", encoding="utf8")

    for tuit in tuits:
        tokens_tuits.append( tokenizer.tokenize( tuit ) )

    tagger = "spanish.tagger"
    jar    = "stanford-postagger.jar"

    etiquetador = StanfordPOSTagger(tagger,jar)

    etiquetas = etiquetador.tag(tokens_tuits[0])

    #for etiqueta in etiquetas:
    #    print(etiqueta)
    i = 1
    for tt in tokens_tuits:
        #etiquetas_opennlp = []
        etiquetas = etiquetador.tag(tt)
        tree = ""
        #print(etiquetas[0][0])
        for etiqueta in etiquetas:
            #etiquetas_opennlp.append(cambiarEtiqueta(etiqueta[1][0]))
            tree = tree + etiqueta[0] + "_" + cambiarEtiqueta(etiqueta[1][0]) + " "

        #etiquetas_tuits.append(etiquetas_opennlp)
        
        archivo.write(tree+"\n")
        print( "Tuit #" + str(i))
        i = i + 1

    archivo.close()

def cambiarEtiqueta(termino):

    switcher = {
        "d" : "DET",
        "f" : ".",
        "p" : "PRON",
        "r" : "ADV",
        "s" : "ADP",
        "v" : "VMS",
        "n" : "NOUN",
        "Date": "NOUN",
        "c" : "CONJ",
        "a" : "ADJ",
        "z" : "NUM",
        "w" : "NUM"
    }

    match = switcher.get(termino)

    if(not match):
        match = "X"

    return match


def main():
    #archivo = open("es-train.pos", "a+", encoding="utf8")
    etiquetarPalabras()
    

#print(switch("d"))
main()