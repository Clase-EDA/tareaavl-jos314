/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BST;

/**
 *
 * @author DELL
 */
class NodoAVL <T extends Comparable<T>> {
    T elem;
    NodoAVL izq;
    NodoAVL der, papa;
    int fe; 
    int altura;
    
       public NodoAVL(){
                
    }
    public NodoAVL(T e){
      elem = e;
      fe = 0;
    }
    
    public NodoAVL getIzq(){
        return izq;
    }
    public NodoAVL getDer(){
        return der;
    }
    
    public NodoAVL getPapa(){
        return papa;
    }
    
    public int getFE(){
        int altDer = 0, altIzq = 0;
        if (der != null) 
            altDer = der.getAltura();
        if (izq != null) 
            altIzq = izq.getAltura();
        
        fe = altDer - altIzq;
        return fe;
    }


    public int altIzq(){
        int resp=0;
        if(izq!=null)
            resp=izq.altIzq()+1;
        return resp;
    }    
    public int altDer(){
        int resp=0;
        if(der!=null)
            resp=der.altDer()+1;
        return resp;
    }
    
    public T getElem (){
        return elem;
    }
    
    public int compareTo(T otroElem){
        return this.compareTo(otroElem);
    }

    public void setElem(T elem) {
        this.elem = elem;
    }

    public void setIzq(NodoAVL izq) {
        this.izq = izq;
    }

    public void setDer(NodoAVL der) {
        this.der = der;
    }

    public void setPapa(NodoAVL papa) {
        this.papa = papa;
    }
    
    public void cuelga (NodoAVL <T> n){
        if (n.getElem().compareTo(this.elem)<0)
            izq = n;
        else
            der= n;
        n.setPapa(this);
    }
    
    public int getAltura(){
            if(this == null)
                return 0;
            return Math.max(altIzq(), altDer());
    }

    public void setFe(int i) {
        fe = i;
    }
}   

