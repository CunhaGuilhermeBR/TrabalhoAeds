import java.util.ArrayList;

public class CArvBin {
 
   private CNo raiz;
    
   public CArvBin() {
      raiz = null;
   }
   
   // Chama o método recursivo para inserção de valores na ABP
   public void adicionar(int valor) {
      raiz = adicionarRecursivo(raiz, valor);
   }
 
   // Método recursivo de inserção de valores na ABP
   private CNo adicionarRecursivo(CNo no, int valor) {
      if (no == null) 
         return new CNo(valor);
    
      if (valor < no.item)
         no.esq = adicionarRecursivo(no.esq, valor);
      else if (valor > no.item)
         no.dir = adicionarRecursivo(no.dir, valor);
      return no;
   }
   
   public void imprimir(int Ordem)
   {
      // Ordem = 1 => EmOrdem
      // Ordem = 2 => PreOrdem
      // Ordem = 1 => PosOrdem     
      switch (Ordem)
      {
         case 1: System.out.println("\n\nImpressao em ordem");
            emOrdem(raiz);
            break;
         case 2: System.out.println("\n\nImpressao pre ordem");
            preOrdem(raiz);
            break;
         case 3: System.out.println("\n\nImpressao pos ordem");
            posOrdem(raiz);
            break;
         default: System.out.println("Opcao invalida!!!\n\n");
            break;
      }
   }
   
   private void emOrdem(CNo no)
   {
      // percorre em ordem crescente
      if (no != null) {
         emOrdem(no.esq);
         System.out.println("Chave = "+no.item);
         emOrdem(no.dir);
      }
   }
 
   private void preOrdem(CNo no)
   {
      // percorre previlegiando a raiz sobre 
      // seus descendentes
      if (no != null) {
         System.out.println("Chave = "+no.item);          
         preOrdem(no.esq);
         preOrdem(no.dir);
      }     
   }
 
   private void posOrdem(CNo no)
   {
      // percorre previlegiando os descendentes 
      // sobre a raiz 
      if (no != null) {
         posOrdem(no.esq);
         posOrdem(no.dir);
         System.out.println("Chave = "+no.item);
      }
   }  
   // Método recursivo de busca em ABP
   private boolean contem(CNo no, int valor) {
      if (no == null)
         return false;
      if (valor == no.item)
         return true;
      return valor < no.item ? contem(no.esq, valor) : contem(no.dir, valor);
   }
 
   // Chama o método recursivo "contem"
   public boolean contem(int valor) {
      return contem(raiz, valor);
   }  
 
   // Método iterativo de busca em ABP
   public boolean achar(int id){
      CNo no = raiz;
      while(no!=null) {
         if(no.item==id) 
            return true;
         else
            if(no.item>id)
               no = no.esq;
            else
               no = no.dir;
      }
      return false;
   }
   //
   public void delete(int valor) {
      raiz = deleteRecursivo(raiz, valor);
   }
   // Método recursivo de exclusão de nós
   private CNo deleteRecursivo(CNo no, int valor) {
      if (no == null)
         return null;
    
      if (valor == no.item) {
         if (no.esq == null && no.dir == null)
            return null;
       
         if (no.dir == null)
            return no.esq;
       
         if (no.esq == null)
            return no.dir;
        
         int menorValor = menorValor(no.dir);
         no.item = menorValor;
         no.dir = deleteRecursivo(no.dir, menorValor);
         return no;       
      }
    
      if (valor < no.item) {
         no.esq = deleteRecursivo(no.esq, valor);
         return no;
      }
      no.dir = deleteRecursivo(no.dir, valor);
      return no;
   }
  
  // Retorna o menor valor em uma subárvore
   private int menorValor(CNo no) {
      return no.esq == null ? no.item : menorValor(no.esq);
   }
   
   public void inserir(int val){
	   CNo no = new CNo(val);
	   inserirAVL(raiz, no);
   }

private void inserirAVL(CNo raiz, CNo no) {
	if (raiz == null) 
		this.raiz = no;
	
	else{
			if(raiz.item > no.item){
				if (raiz.esq == null) {
					raiz.esq=no;
					no.setPai(raiz);
					verificarBalanceamento(raiz);

				} else {
					inserirAVL(raiz.esq, no);
				}

			} else if (no.item > raiz.item) {

				if (raiz.dir == null) {
					raiz.dir=no;
					no.setPai(raiz);
					verificarBalanceamento(no);

				} else {
					inserirAVL(raiz.dir, no);
				}

			}
		}
	
}

public void verificarBalanceamento(CNo atual) {
	verificarBalanceamento(atual);
	int balanceamento = atual.getBalanceamento();
	
	if (balanceamento == -2) {

		if (altura(atual.esq.esq) >= altura(atual.esq.dir)) {
			atual = rotacaoDireita(atual);

		} else {
			atual = duplaRotacaoEsquerdaDireita(atual);
		}

	} else if (balanceamento == 2) {

		if (altura(atual.dir.dir) >= altura(atual.dir.esq)) {
			atual = rotacaoEsquerda(atual);

		} else {
			atual = duplaRotacaoDireitaEsquerda(atual);
		}
	}

	if (atual.getPai() != null) {
		verificarBalanceamento(atual.getPai());
	} else {
		this.raiz = atual;
	}
}
    

  private CNo duplaRotacaoDireitaEsquerda(CNo inicial) {
	    inicial.dir=(rotacaoDireita(inicial.dir));
		return rotacaoEsquerda(inicial);
}

    private CNo rotacaoEsquerda(CNo inicial) {
	

		CNo direita = inicial.dir;
		direita.setPai(inicial.getPai());

		inicial.dir=direita.esq;

		if (inicial.dir != null) {
			inicial.dir.setPai(inicial);
		}

		direita.esq=inicial;
		inicial.setPai(direita);

		if (direita.getPai() != null) {

			if (direita.getPai().dir == inicial) {
				inicial.dir.setPai(inicial);
			
			} else if (direita.getPai().esq == inicial) {
				inicial.esq.setPai(inicial);
			}
		}

		verificarBalanceamento(inicial);
		verificarBalanceamento(direita);

		return direita;
	}



private CNo duplaRotacaoEsquerdaDireita(CNo inicial) {
	inicial.esq=(rotacaoEsquerda(inicial.esq));
	return rotacaoDireita(inicial);
}

private CNo rotacaoDireita(CNo inicial) {
	CNo esquerda = inicial.esq;
	esquerda.setPai(inicial.getPai());

	inicial.esq=esquerda.dir;

	if (inicial.esq != null) {
		inicial.esq.setPai(inicial);
	}

	esquerda.dir=inicial;
	inicial.setPai(esquerda);

	if (esquerda.getPai() != null) {

		if (esquerda.getPai().dir == inicial) {
			esquerda.dir=esquerda.getPai();
		
		} else if (esquerda.getPai().esq == inicial) {
			esquerda.esq=esquerda.getPai();
		}
	}

	verificarBalanceamento(inicial);
	verificarBalanceamento(esquerda);

	return esquerda;
}

private int altura(CNo atual) {
	if (atual == null) {
		return -1;
	}

	if (atual.esq == null && atual.dir == null) {
		return 0;
	
	} else if (atual.esq== null) {
		return 1 + altura(atual.dir);
	
	} else if (atual.dir == null) {
		return 1 + altura(atual.esq);
	
	} else {
		return 1 + Math.max(altura(atual.esq), altura(atual.dir));
	}
}
}
