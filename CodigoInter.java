package Fernando.CopiladorGit;

import java.util.LinkedList;

public class CodigoInter {

    private LinkedList<String> entrada = new LinkedList<String>();
    private LinkedList<String> conver = new LinkedList<String>();
    private LinkedList<DatosIDS> dat = new LinkedList<DatosIDS>();
    private Datos obd = new Datos();

    private String expDec = ("int|float|char|String|,"),
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

    public LinkedList<String> Convertir() {
        LinkedList<String> tempSwWh = new LinkedList<String>();
        int x, pos = 0, caso = 2, sw = -1, wh = -1, Vwh = 0, ultV = -1, y;
        String cad = "", tempG = "", ant = "", bloqueant="",eleremove="";
        entrada.add("$");
        LinkedList<String> pila = new LinkedList<String>();

        while (!entrada.getFirst().equals("$")) {
            cad = "";
            tempG = "";

            if (entrada.getFirst().matches(expDec))//Declaraciones
            {
                if (!entrada.getFirst().equals(",")) {
                    ant = entrada.getFirst();
                    entrada.removeFirst();
                    pila.add(ant + " " + entrada.removeFirst() + ";");
                } else {
                    entrada.removeFirst();
                    pila.add(ant + " " + entrada.removeFirst() + ";");
                }
            } else if (entrada.getFirst().matches(expId))//Asignaciones
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
                                    pila.add("V" + (pos++) + "= " + entrada.get(x - 2) + ";");//0
                                    //pila.add("float " + "V" + pos + ";");
                                    pila.add("V" + (pos--) + "= " + entrada.get(x - 1) + ";");//1
                                    pila.add("V" + (pos) + "= V" + (pos) + " " + entrada.get(x) + " V" + (++pos) + ";");//0-0-1
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
                                    if (!tempSwWh.isEmpty()) {
                                        if (tempSwWh.getLast().equals("sw")) {
                                            pos -= 2;
                                        }
                                    }
                                } else if (x == 1) {
                                    obd.Println("bbb");
                                    pos = pos - 1;
                                    pila.add("V" + (pos) + "= V" + (pos) + "" + entrada.remove(x) + "" + entrada.remove(x - 1) + ";");
                                    pos = pos + 1;
                                    bloqueant = "b";
                                    if (!tempSwWh.isEmpty()) {
                                        if (tempSwWh.getLast().equals("sw")) {
                                            pos -= 2;
                                        }
                                    }
                                } else {
                                    if (pos <= 3) {
                                        obd.Println("ccc");

                                        if (bloqueant=="c"){
                                            pila.removeLast();
                                            pos = pos - 3;
                                            pila.add("V" + (pos) + "= V" + (pos) + "" + eleremove + "V" + (pos + 1) + ";");
                                            pila.add("V" + (pos) + "= V" + (pos) + "" + entrada.removeFirst() + "V" + (pos + 2) + ";");
                                            pos = pos + 3;
                                        }
                                        else
                                        {
                                            pos = pos - 2;
                                            eleremove=entrada.removeFirst();
                                            pila.add("V" + (pos) + "= V" + (pos) + "" + eleremove + "V" + (pos + 1) + ";");
                                            pos = pos + 2;
                                        }
                                        bloqueant = "c";
                                                                                
                                    } else {
                                        obd.Println("ddd");
                                        pos = pos - 3;
                                        pila.add("V" + (pos) + "= V" + (pos) + "" + entrada.removeFirst() + "V" + (pos + 1) + ";");
                                        pos = pos + 3;
                                        bloqueant = "d";
                                    }
                                    if (!tempSwWh.isEmpty()) {
                                        if (tempSwWh.getLast().equals("sw")) {
                                            pos -= 4;
                                        }
                                    }
                                }
                            } else {
                                for (y = x + 1; y < entrada.size() && !expOp.contains(entrada.get(y)); y++);
                                if ((y - (x + 1)) >= 2) {

                                } else if ((y - (x + 1)) == 1) {
                                    pila.add("V" + (ultV) + "= " + "V" + (ultV) + " " + entrada.get(y) + " " + entrada.get(y - 1) + ";");//0
                                    entrada.remove(y);
                                    entrada.remove(y - 1);
                                    obd.Println("mensaje chingon 1");
                                    obd.Println(1 + ">..............." + entrada.get(y));
                                    this.Mostrar();
                                    if (entrada.get(y).equals(";")) {
                                        break;
                                    }

                                } else {
                                    pila.add("V" + (ultV) + "= " + entrada.get(y - 2) + " " + entrada.get(y) + " V" + (ultV) + ";");//0
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
                pila.add(cad);
            } else if (entrada.getFirst().equals(";")) {
                entrada.removeFirst();
            } else if (entrada.getFirst().equals("}")) {
//                if (!tempSwWh.isEmpty()) {
//                    if (tempSwWh.getLast().equals("wh")) {
//                        tempSwWh.removeLast();
//                        cad = "goto while" + (wh) + ";";
//                        pila.add(cad);
//                        cad = "Fin while" + (wh--) + ":";
//                        pila.add(cad);
//                    } else {
//                        tempSwWh.removeLast();
//                        cad = "Fin_Switch" + (sw--) + ":";
//                        pila.add(cad);
//                        entrada.removeFirst();
//
//                    }
//                } else {
//                    pila.add(entrada.removeFirst());
//                }
            } else if (entrada.getFirst().equals("#")) {
                entrada.removeFirst();
            }

        }
        entrada.clear();
        return pila;

    }

    private void mostrarPilas(LinkedList<String> pila) {
        System.out.println("Codigo Intermedio---Mostrar-->");
        for (int x = 0; x < pila.size(); x++) {
            System.out.println(pila.get(x));
        }
    }
}
