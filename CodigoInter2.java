package Fernando.Abel;

import java.util.LinkedList;

public class CodigoInter2 {

    private LinkedList<String> entrada = new LinkedList<String>();
    private LinkedList<String> conver = new LinkedList<String>();
    private LinkedList<DatosIDS> dat = new LinkedList<DatosIDS>();
    private Datos obd = new Datos();
    private int contIf=0, contPara=0;

    private String expDec = ("entero|flotante|caracter"),
    		operador = "+-/*;",
            expId = ("[a-z]([a-z]|[A-Z])*[0-9]*"),
            expcad = ("~([a-z]|[A-Z]|[0-9])*~"),
            expchar = ("'([a-z]|[A-Z])'"),
            expOp = "*/+-#;";

    public void Elemento(String cad) {
        entrada.add(cad);
    }

    public void ListElemtos(LinkedList<String> list) {
        entrada.addAll(list);
    }

    public void Mostrar() {
        this.mostrarPilas(entrada);
    }

    public void Datos(LinkedList<DatosIDS> dat) {
        this.dat.addAll(dat);
    }

    public LinkedList<String> Bloque() {
    	conver.clear();
    	if(entrada.getFirst().equals("!START!")) {
    		conver.add("int main() {");
    		entrada.removeFirst();
    	}
    	if(expDec.contains(entrada.getFirst())){
    		conver.add(1,"");
    		conver.add(2,"");
    		conver.add(3,"");
    		int ant = -1;
    		String temp;
    		while(!entrada.getFirst().equals("{"))
    		{
    			if(entrada.getFirst().equals("entero")){
    				ant = 1;
    				temp = conver.get(ant);
    				entrada.removeFirst();
    				if(temp!="") {
    					conver.remove(ant);
    					conver.add(ant, temp + " , "+ entrada.removeFirst());
    				}
    				else {
    					conver.remove(ant);
    					conver.add(ant, "int "+ entrada.removeFirst());
    				}
    					
    			}
    			else if(entrada.getFirst().equals("flotante")){
    				ant = 2;
    				temp = conver.get(ant);
    				entrada.removeFirst();
    				if(temp!="") {
    					conver.remove(ant);
    					conver.add(ant, temp + " , "+ entrada.removeFirst());
    				}
    				else {
    					conver.remove(ant);
    					conver.add(ant, "float "+ entrada.removeFirst());
    				}
    			}
				else if(entrada.getFirst().equals("caracter")){
					ant = 3;		
					temp = conver.get(ant);
    				entrada.removeFirst();
    				if(temp!="") {
    					conver.remove(ant);
    					conver.add(ant, temp + " , "+ entrada.removeFirst());
    				}
    				else {
    					conver.remove(ant);
    					conver.add(ant, "char "+ entrada.removeFirst());
    				}
				}
				else if(entrada.getFirst().equals(",")){
					temp = conver.get(ant);
					entrada.removeFirst();
					conver.remove(ant);
					conver.add(ant, temp + " , "+ entrada.removeFirst());
				}
				else {
					entrada.removeFirst();
				}
    			
    		}
    		entrada.removeFirst();
    		//Este bloque era para quitar los espacios 
    		/*if(conver.get(1).equals("")) { 
    			conver.remove(1);
    		}
    		if(conver.get(2).equals("")) {
    			conver.remove(2);
    		}
			if(conver.get(3).equals("")) {
				conver.remove(3);
			}*/
    		
    		this.Bloque2();
    		entrada.removeFirst();//Eliminamos restos
    		entrada.removeFirst();//Eliminamos restos
    		conver.add("}");
    		
    		
    		
    	}
    	
    	return conver;
    }
    
    private void Bloque2() {
    	//this.mostrarPilas(entrada);
    	while(entrada.getFirst()!="}")
		{
			if (entrada.getFirst().equals("si")) {
				this.BloqIf();
			}
			else if (entrada.getFirst().equals("para")) {
				obd.Println("Entra para");
				this.BloqFor();
			}
			else if (entrada.getFirst().equals("imp")) {
				this.BloqImp();
			}else if (entrada.getFirst().equals("finasigna")) {
				entrada.removeFirst();// Se elimina finasigna y se retorna
				return;
			}else if(entrada.getFirst().matches(expId)) {
				this.BloqAsigna();
			}
		}
    }
    
    
    private void BloqAsigna() {
    	String idAsig = "",var1,var2,op;
    	int x,contV=0;
    	idAsig = entrada.removeFirst();//Se extra la id a la cual se va asignar
    	entrada.removeFirst();// y el simbolo asignador 
    	
    	//this.mostrarPilas(entrada);
    	while(!entrada.getFirst().equals(";")) {
    		for(x = 0;x<entrada.size() && !operador.contains(entrada.get(x));x++);
    		//obd.Println("("+x+"/"+entrada.get(x)+")");
        	if(entrada.get(x).equals(";")) {
        		conver.add("//////////////////////////////////////////");
            	var2 = entrada.remove(x-1);//Elimina y extra idvariable 
        		conver.add(idAsig+" = "+var2+";");
        	}else {
        		op = entrada.remove(x);
        		var2 = entrada.remove(x-1);
        		var1 = entrada.remove(x-2);//Esta es la q carga  var1 / var2 
        		
        		conver.add("float V"+contV+";");
        		conver.add("V"+(contV++)+" = "+var1+";");
        		conver.add("float V"+contV+";");
        		conver.add("V"+contV+" = "+var2+";");      
        		conver.add("V"+(contV-1)+" = V"+(contV-1)+" "+op+" V"+(contV)+";");
        		conver.add("//////////////////////////////////////////");
        		entrada.add((x-2),"V"+(contV-1));
        	}		
    	}
    	entrada.removeFirst();
    }
    
    private void BloqIf() {
    	contIf++;
    	String op="",ban="";
    	//conver.add("et_If"+(contIf++)+":");
    	entrada.removeFirst();// Se elimina Si
    	entrada.removeFirst();// Se elimina (
    	conver.add("cond1if"+contIf+" = "+entrada.removeFirst());// Se elimina id
    	op = entrada.removeFirst();// Se elimina comparador
    	conver.add("cond2if"+contIf+" = "+entrada.removeFirst());// Se elimina id
    	conver.add("condfin"+contIf+" = cond1if"+contIf+" "+op+" cond2if"+contIf);
    	conver.add("if(!condfin"+contIf+")");
    	conver.add("goto Else"+contIf+";");
    	entrada.removeFirst();// Se elimina )
    	entrada.removeFirst();// Se elimina then
    	this.Bloque2();
    	if(entrada.getFirst().equals("sino"))
    		ban = entrada.removeFirst();// Se elimina sino
		conver.add("Else"+contIf+":");
		if (ban!="")
			this.Bloque2();
    	/*if(entrada.getFirst().equals("sino")) {
    		entrada.removeFirst();// Se elimina sino
    		conver.add("Else"+contIf+":");
    		this.Bloque2();
    	}*/
    	entrada.removeFirst();// Se elimina finsi
    	conver.add("goto endIf"+contIf+";");
    	conver.add("endIf"+contIf+":");
    	contIf--;
    }
    
    private void BloqFor() {
    	this.mostrarPilas(entrada);
    	contPara++;
    	String op="",var1,var2,varinc,cadinc;
    	entrada.removeFirst();// Se elimina para
    	entrada.removeFirst();// Se elimina (
    	obd.Println(entrada.getFirst());
    	if(entrada.getFirst().equals("entero")) {
    		entrada.removeFirst();//se elemino tipo de dato
    		conver.add("int "+entrada.getFirst()+";");
    	}else if (entrada.getFirst().equals("flotante")) {
    		entrada.removeFirst();
    		conver.add("float "+entrada.getFirst()+";");
    	}else if (entrada.getFirst().equals("caracter")) {
    		entrada.removeFirst();
    		conver.add("char "+entrada.getFirst()+";");
    	}else
    	{
    		obd.Println("Error");
    	}
    	var1 = entrada.removeFirst();//se elimina var1
    	entrada.removeFirst();//se elimina asignador
    	var2 = entrada.removeFirst();//se elimina var2
    	conver.add(var1+" = "+var2+";");
    	entrada.removeFirst();//se elimina ;
    	/////////////////////////////////////////////////////////////////////////////////
    	var1 = entrada.removeFirst();//se elimina var1cond
    	op = entrada.removeFirst();//se elimina comparador
    	var2 = entrada.removeFirst();//se elimina var2cond
    	entrada.removeFirst();//se elimina ;
    	conver.add("For"+contPara+":");
    	conver.add("float cond1for"+contPara+";");
    	conver.add("float cond2for"+contPara+";");
    	conver.add("float resfor"+contPara+";");
    	conver.add("cond1for"+contPara+" = "+var1+";");
    	conver.add("cond2for"+contPara+" = "+var2+";");
    	conver.add("resfor"+contPara+" = cond1for"+contPara+" "+op+" = cond2for"+contPara+";");
    	varinc = entrada.removeFirst();//se elimina la variable a incrementar 
    	if(entrada.getFirst().equals("++")) {
    		cadinc = varinc+" = "+varinc+" + 1;";
    		entrada.removeFirst();//se elimina incremeneto++
    	}
    	else if(entrada.getFirst().equals("--")){
    		cadinc = varinc+" = "+varinc+" - 1;";
    		entrada.removeFirst();//se elimina incremeneto++
    	}
    	else {
    		cadinc = varinc+" = "+varinc+" "+entrada.removeFirst()+" "+entrada.removeFirst()+";";//se elimina operador y la id de aumento
    	}
    	entrada.removeFirst();//se elimina )
    	conver.add("if(!resfor"+contPara+")");
    	conver.add("goto finfor"+contPara+";");
    	
    	this.Bloque2();
    	
    	conver.add(cadinc);
    	conver.add("goto For"+contPara+";");
    	conver.add("finfor"+contPara+":");
    	entrada.removeFirst();// Se elimina finpara
    	
    }
    
    private void BloqImp() {
    	entrada.removeFirst();//se elimina imp
    	conver.add("printf("+ entrada.removeFirst()+");");//se elimina variable 
    	entrada.removeFirst();//se elimina ;
    }
    
    /*
    private void BloqAsigna() {
    	String tempG = "",cad="",bloqueant="",eleremove="";
    	int x, pos = 0,ultV = -1,y;
    	if (entrada.getFirst().matches(expId))//Asignaciones
        {
            tempG = entrada.getFirst();
            cad = entrada.removeFirst();
            if (entrada.getFirst().equals("=")) {
                cad = cad + entrada.removeFirst() + "V" + pos + ";";

            }
            while (!entrada.getFirst().equals(";")) {
                if (!entrada.getFirst().matches(expcad)) {

                    for (x = 0; x < entrada.size() && !expOp.contains(entrada.get(x)); x++);
                    if (!entrada.get(x).equals(";")) {
                        if (!entrada.get(x).equals("#")) {
                            if (x >= 2) {
                                obd.Println("aaa");
                                //pila.add("float " + "V" + pos + ";");
                                conver.add("V" + (pos++) + "= " + entrada.get(x - 2) + ";");//0
                                //pila.add("float " + "V" + pos + ";");
                                conver.add("V" + (pos--) + "= " + entrada.get(x - 1) + ";");//1
                                conver.add("V" + (pos) + "= V" + (pos) + " " + entrada.get(x) + " V" + (++pos) + ";");//0-0-1
                                entrada.remove(x);
                                if (x > 2) {
                                    entrada.add(x, "#");
                                }
                                entrada.remove(x - 1);
                                entrada.remove(x - 2);

                                ultV = pos-1;//Agregue
                                this.Mostrar();

                                bloqueant = "a";
                                //pos++;

                            } else if (x == 1) {
                                obd.Println("bbb");
                                pos = pos - 1;
                                conver.add("V" + (pos) + "= V" + (pos) + "" + entrada.remove(x) + "" + entrada.remove(x - 1) + ";");
                                pos = pos + 1;
                                bloqueant = "b";
                            } else {
                                if (pos <= 3) {
                                    obd.Println("ccc");

                                    if (bloqueant=="c"){
                                    	conver.removeLast();
                                        pos = pos - 3;
                                        conver.add("V" + (pos) + "= V" + (pos) + "" + eleremove + "V" + (pos + 1) + ";");
                                        conver.add("V" + (pos) + "= V" + (pos) + "" + entrada.removeFirst() + "V" + (pos + 2) + ";");
                                        pos = pos + 3;
                                    }
                                    else
                                    {
                                        pos = pos - 2;
                                        eleremove=entrada.removeFirst();
                                        conver.add("V" + (pos) + "= V" + (pos) + "" + eleremove + "V" + (pos + 1) + ";");
                                        pos = pos + 2;
                                    }
                                    bloqueant = "c";
                                                                            
                                } else {
                                    obd.Println("ddd");
                                    pos = pos - 3;
                                    conver.add("V" + (pos) + "= V" + (pos) + "" + entrada.removeFirst() + "V" + (pos + 1) + ";");
                                    pos = pos + 3;
                                    bloqueant = "d";
                                }
                            }
                        } else {
                            for (y = x + 1; y < entrada.size() && !expOp.contains(entrada.get(y)); y++);
                            if ((y - (x + 1)) >= 2) {

                            } else if ((y - (x + 1)) == 1) {
                                conver.add("V" + (ultV) + "= " + "V" + (ultV) + " " + entrada.get(y) + " " + entrada.get(y - 1) + ";");//0
                                entrada.remove(y);
                                entrada.remove(y - 1);
                                obd.Println("mensaje chingon 1");
                                obd.Println(1 + ">..............." + entrada.get(y));
                                this.Mostrar();
                                if (entrada.get(y).equals(";")) {
                                    break;
                                }

                            } else {
                                conver.add("V" + (ultV) + "= " + entrada.get(y - 2) + " " + entrada.get(y) + " V" + (ultV) + ";");//0
                                entrada.remove(y);
                                entrada.remove(y - 2);
                                obd.Println("mensaje chingon 2");
                                obd.Println(0 + ">..............." + entrada.get(y - 1));
                                this.Mostrar();
                                if (entrada.get(y - 1).equals(";")) {
                                    break;
                                }

                            }
                        }
                    } else {
                        cad = tempG + "=" + entrada.removeFirst() + ";";
                        obd.Println(entrada.getFirst() + ">>>>>>>>>>>>>");
                    }
                } else {
                    if (!entrada.getFirst().matches(expcad)) {
                        for (x = 0; x < dat.size() && !dat.get(x).getId().equals(tempG); x++);
                        obd.Println(dat.get(x).getId());
                        if (dat.get(x).getTip().equals("0")) {
                            cad = "Scanf(\"%d\", &" + tempG + ");";
                        } else if (dat.get(x).getTip().equals("1")) {
                            cad = "Scanf(\"%lf\", &" + tempG + ");";
                        } else if (dat.get(x).getTip().equals("2")) {
                            cad = "Scanf(\"%c\", &" + tempG + ");";
                        } else {
                            cad = "Scanf(\"%s\", &" + tempG + ");";
                        }
                        entrada.removeFirst();
                    } else {
                        cad = tempG + "= \"" + entrada.getFirst().substring(1, entrada.removeFirst().length() - 1) + "\" ;";
                    }
                }

            }
            entrada.removeFirst();
            conver.add(cad);
        }
    }
    */
   

    private void mostrarPilas(LinkedList<String> pila) {
        System.out.println("Codigo Intermedio---Mostrar-->");
        for (int x = 0; x < pila.size(); x++) {
            System.out.println(pila.get(x));
        }
    }
}




























