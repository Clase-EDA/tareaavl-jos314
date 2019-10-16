/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BST;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author DELL
 */
public class ArbolAVL <T extends Comparable<T>> {
    
    protected NodoAVL raiz;
    private int cont;
    
    public ArbolAVL() {
        cont = 0;
    }
    
    public ArbolAVL(T elem){
        raiz.setElem(elem);
        cont = 1;
    }

    public NodoAVL getRaiz() {
        return raiz;
    }

    public int getCont() {
        return cont;
    }
    
    
    public void actualizaFe(NodoAVL <T> nod) {
        if (nod == null) {
            return;
        }
        if (nod.izq != null) {
            actualizaFe(nod.izq);
        }
        if (nod.der != null) {
            actualizaFe(nod.der);
        }
        nod.setFe(height(nod.der) - height(nod.izq));
        
    }
    
    public int altura(NodoAVL <T> actual) {
        if (actual == null) {
            return -1;
        } else {
            return actual.getAltura();
        }
    }
    
    public boolean insert(T elem){
        boolean t = false;
        NodoAVL <T> nod ;
        NodoAVL <T> aux;
        if (elem != null){
            nod = new NodoAVL (elem);
            if (raiz == null){
                raiz = nod;
            }   
            else{
                    aux = buscar (raiz, elem);
                    aux.cuelga(nod);  
                          
                    actualizaFe(aux);
                    rotacion( aux);
                    actualizaFe(aux);
                }  
            t = true;
            cont++;
            } 
        return t;
    }
    
        /*private NodoAVL<T> insert(T elemento, NodoAVL<T> actual) {
        if (actual == null) {
            actual = new NodoAVL<T>(elemento);
            actual.setFe(height(actual.der) - height(actual.izq));
        } else {
            if (elemento.compareTo(actual.elem) < 0) {
                actual.izq = insert(elemento, actual.izq);
                if (height(actual.izq) - height(actual.der) == 2) {
                    if (actual.compareTo((T) actual.izq) < 0) {
                        actual = rotateLeft(actual);
                    } else {
                        actual = rotateDoubleLeft(actual);
                    }
                }
            } else {
                actual.der = insert(elemento, actual.der);
                if (height(actual.der) - height(actual.izq) == 2) {
                    if (actual.compareTo((T) actual.der.elem) > 0) {
                        actual = rotateRight(actual);
                    } else {
                        actual = rotateDoubleRight(actual);
                    }
                }
            }
        }
        actual.altura = Math.max(height(actual.izq), height(actual.der)) + 1;
        actual.setFe(height(actual.der) - height(actual.izq));
        return actual;
    }
    */

    
        public boolean elimina(T elem){
        NodoAVL nod = buscar(raiz, elem);
        NodoAVL aux;
        if (nod == null)
            return false;
        //Si la raiz es una hoja
        if (nod.getDer()==null && nod.getIzq()==null){
            if (nod.getPapa()!=null){
                aux = nod.getPapa();
                if (nod.getElem().compareTo(nod.getPapa().getElem())<0)
                    nod.getPapa().setIzq(null);
                else
                    nod.getPapa().setDer(null);        
                aux.getFE();
                rotacion(aux);
                actualizaFe (nod);
            }
        }
        else//Si solo tiene un hijo
            if (nod.getIzq()==null){
            if (nod == raiz){
                raiz = nod.getDer();
                raiz.setPapa(null);
            }else
                nod.getPapa().cuelga(nod.getDer());
                            actualizaFe(nod);

            }else
            if (nod.getDer()==null){
            if(nod == raiz){
                raiz = nod.getIzq();
                raiz.setPapa(null);
            }else
                nod.getPapa().cuelga(nod.getIzq());
                actualizaFe(nod);

            }
            else{//Si tiene dos hijos. 
                NodoAVL<T> suc = nod.getDer();
                while(suc.getIzq()!=null)
                    suc = suc.getIzq();
                nod.setElem(suc.getElem());
                if(suc == nod.getDer())
                    nod.setDer(suc.getDer());
                else
                    suc.getPapa().setIzq(suc.getDer());
                actualizaFe(nod);
            }        
        cont--;
            return true;
    }
    
    private NodoAVL<T> buscar (NodoAVL <T> n, T elem){
        boolean enc = false;
        NodoAVL <T> aux  = raiz;
        while(!enc && n!=null){
            aux = n;
            if (elem.compareTo(n.getElem())<0)
                n=n.getIzq();
                else
                if(elem.compareTo(n.getElem())>0)
                    n=n.getDer();
                    else
                    enc = true;
        }
        return aux;
    }
    

    
    private NodoAVL<T> rotacion (NodoAVL<T> n){
        // izq - izq
        
        NodoAVL <T> res = null;
        if (n.getFE() == -2 && (n.izq.getFE()  == -1 || n.izq.getFE() == 0)){
            NodoAVL<T> alfa = n;
            NodoAVL<T> papa = n.getPapa();
            NodoAVL<T> beta  = alfa.getIzq();
            NodoAVL<T> gamma = beta.getIzq();
            NodoAVL<T> a = gamma.getIzq();
            NodoAVL<T> b = gamma.getDer();
            NodoAVL<T> c = beta.getDer();
            NodoAVL<T> d = alfa.getDer();
            
            gamma.cuelga(a);
            gamma.cuelga(b);
            alfa.cuelga(c);
            alfa.cuelga(d);
            beta.cuelga(gamma);
            beta.cuelga(alfa);
            if (papa == null)
                raiz = beta;
            else
                papa.cuelga(beta);
            res = beta;
        }
        else
            if(n.getFE() == -2 && (n.izq.getFE()  == 1 )){
                NodoAVL<T> alfa = n;
                NodoAVL<T> papa = n.getPapa();
                NodoAVL<T> beta  = alfa.getIzq();
                NodoAVL<T> gamma = beta.getDer();
                NodoAVL<T> a = beta.getIzq();
                NodoAVL<T> b = gamma.getIzq();
                NodoAVL<T> c = gamma.getDer();
                NodoAVL<T> d = alfa.getDer();    

                beta.cuelga(a);
                beta.cuelga(b);
                alfa.cuelga(c);
                alfa.cuelga(d);
                gamma.cuelga(beta);
                gamma.cuelga(alfa);
                if (papa == null)
                    raiz = gamma; 
                else
                    papa.cuelga(gamma);
                res = gamma;
            }
        else
            if(n.getFE() == 2 && (n.izq.getFE()  == -1 )){ 
                NodoAVL<T> alfa = n;
                NodoAVL<T> papa = n.getPapa();
                NodoAVL<T> beta  = alfa.getDer();
                NodoAVL<T> gamma = beta.getIzq();
                NodoAVL<T> a = alfa.getIzq();
                NodoAVL<T> b = gamma.getIzq();
                NodoAVL<T> c = gamma.getDer();
                NodoAVL<T> d = beta.getDer();    
                
                alfa.cuelga(a);
                alfa.cuelga(b);
                beta.cuelga(c);
                beta.cuelga(d);
                gamma.cuelga(beta);
                gamma.cuelga(alfa);
                if (papa == null)
                    raiz = gamma;  
                else
                    papa.cuelga(gamma);
                res = gamma;
            }
        else
            if(n.getFE() == 2 && (n.izq.getFE()  == 1 || n.izq.getFE() == 0)){
                NodoAVL<T> alfa = n;
                NodoAVL<T> papa = n.getPapa();
                NodoAVL<T> beta  = alfa.getDer();
                NodoAVL<T> gamma = beta.getDer();
                NodoAVL<T> a = alfa.getIzq();
                NodoAVL<T> b = beta.getIzq();
                NodoAVL<T> c = gamma.getIzq();
                NodoAVL<T> d = gamma.getDer();    

                alfa.cuelga(a);
                alfa.cuelga(b);
                gamma.cuelga(c);
                gamma.cuelga(d);
                beta.cuelga(gamma);
                beta.cuelga(alfa);
                if (papa == null)
                    raiz = beta; 
                else
                    papa.cuelga(beta);
                res = beta;
            }        
        return res;
    } 
    /*
     public NodoAVL <T> rotacion(T elemento, NodoAVL<T> actual) {
        if (actual == null) {
            return actual;
        } else {
            if (elemento.compareTo(actual.elem) < 0) {
                actual.izq = rotacion(elemento, actual.izq);
                if (actual.getFE() == -2) {
                    if (actual.der == null) {
                        actual = rotateLeft(actual);
                    } else {
                        actual = rotateDoubleLeft(actual);
                    }
                }
                if (actual.getFE() == 2) {
                    if (actual.izq == null) {
                        actual = rotateRight(actual);
                    } else {
                        actual = rotateDoubleRight(actual);
                    }
                }
            } else {
                actual.der = rotacion(elemento, actual.der);
                if (actual.getFE() == -2) {
                    if (actual.der == null) {
                        actual = rotateLeft(actual);
                    } else {
                        actual = rotateDoubleLeft(actual);
                    }
                }
                if (actual.getFE() == 2) {
                    if (actual.izq == null) {
                        actual = rotateRight(actual);
                    } else {
                        actual = rotateDoubleRight(actual);
                    }
                }
            }
        }
        actual.altura = Math.max(height(actual.izq), height(actual.der)) + 1;
        actual.setFe(height(actual.der) - height(actual.izq));
        return actual;
    }*/
     
     public NodoAVL<T> rotateLeft(NodoAVL<T> x1) {
        NodoAVL<T> x2 = x1.izq;
        x1.izq = x2.der;
        x2.der = x1;
        x1.altura = Math.max(height(x1.izq), height(x1.der)) + 1;
        x2.altura = Math.max(height(x2.izq), x1.altura) + 1;
        return (x2);
    }

    public NodoAVL<T> rotateDoubleLeft(NodoAVL<T> x1) {
        x1.izq = rotateRight(x1.izq);
        return rotateLeft(x1);
    }

    public NodoAVL<T> rotateRight(NodoAVL<T> x1) {
        NodoAVL<T> x2 = x1.der;
        x1.der = x2.izq;
        x2.izq = x1;
        x1.altura = Math.max(height(x1.izq), height(x1.der)) + 1;
        x2.altura = Math.max(height(x2.der), x1.getAltura()) + 1;
        return (x2);
    }

    public NodoAVL<T> rotateDoubleRight(NodoAVL<T> x1) {
        x1.der = rotateLeft(x1.der);
        return rotateRight(x1);
    }

    public int height(NodoAVL<T> actual) {
        if (actual == null) {
            return -1;
        } else {
            return actual.getAltura();
        }
    }

    

    public void imprime() {
        StringBuilder sb = new StringBuilder();
        ArrayList<NodoAVL<T>> aux = new ArrayList<>();
        ArrayList<T> lista = new ArrayList<>();
        ArrayList lista2 = new ArrayList<>();
        aux.add(raiz);
        NodoAVL<T> temp;
        while (!aux.isEmpty()) {
            temp = aux.remove(0);
            lista.add(temp.getElem());
            lista2.add(temp.getFE());
            if (temp.getIzq() != null) {
                aux.add(temp.getIzq());
            }
            if (temp.getDer() != null) {
                aux.add(temp.getDer());
            }
        }
        while (lista.iterator().hasNext()) {
            System.out.println(lista.remove(0));
            System.out.println(lista2.remove(0));
        }
    }
    
    
    
    
    public static void main(String [] args){
        ArbolAVL arbol = new ArbolAVL();
        Integer a = 12;
        Integer b = 13;
        Integer c = 14;
        Integer d = 15;
        Integer e = 16;
        Integer f = 17;        
        Integer g = 18;
        arbol.insert(b);
        arbol.insert(c);
        arbol.insert(d);
        arbol.insert(e);
        arbol.insert(f);
        arbol.insert(g);
        arbol.insert(a);
        
        arbol.imprime();
        
  /*     
        Integer busc = (Integer) arbol.buscar(arbol.raiz, a).elem;
        System.out.println("Elemento a  = " + busc);
        System.out.println("Elemento raiz  = " + arbol.raiz.elem);

            a.insertar(10);
    a.insertar(5);
    a.insertar(15);
    a.imprime();
    a.insertar(25);
    a.insertar(35);
    a.imprime();
        a.imprime();
    }

*/

}
}
