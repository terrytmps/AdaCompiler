package org.pcl.structure.tds;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.ArrayList;
import java.util.List;

/** Represent the TDS. */
public class Tds {

    private static int REGION_COUNTER = 0;

    private List<Symbol> symbols;

    private List<Tds> child;

    private Tds parent;

    private int imbrication;

    private int region;

    private String name;

    public Tds(List<Symbol> symbols, String name) {
        this.symbols = symbols;
        this.parent = null;
        this.child = new ArrayList<>();
        imbrication = 0;
        region = REGION_COUNTER;
        REGION_COUNTER++;
        this.name = name;
    }

    public void addChild(Tds tds) {
        tds.parent = this;
        tds.imbrication = tds.parent.imbrication + 1;
        child.add(tds);
    }

    public Tds(String name) {
        this(new ArrayList<>(), name);
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public Tds getParent() {
        return parent;
    }

    public void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    public int getImbrication() {
        return imbrication;
    }

    public int getRegion() {
        return region;
    }

    public String getName() {
        return name;
    }

    public List<Tds> getChild() {
        return child;
    }

    public void setChild(List<Tds> child) {
        this.child = child;
    }

    public void setParent(Tds parent) {
        this.parent = parent;
    }

    public void setImbrication(int imbrication) {
        this.imbrication = imbrication;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }

    public void addSymbols(List<Symbol> symbols) {
        this.symbols.addAll(symbols);
    }

    public void addSymbols(Tds tds) {
        this.symbols.addAll(tds.getSymbols());
    }

    public boolean containsSymbol(String SymbolName) {
        for (Symbol symbol : symbols) {
            if (symbol.getName().equals(SymbolName)) {
                return true;
            }
        }
        boolean a = parent.containsSymbol(SymbolName);
        if (a) {
            return true;
        }
        return false;
    }

    public Symbol getSymbol(String SymbolName) {
        for (Symbol symbol : symbols) {
            if (symbol.getName().equals(SymbolName)) {
                return symbol;
            }
        }
        if (parent != null)
            return parent.getSymbol(SymbolName);
        
        return null;
    }

    @Override
    public String toString() {
        System.out.println("\n");
        // AsciiTable asciiTable = new AsciiTable();
        // asciiTable.addRule();
        // asciiTable.addRow("TDS - Région:" + region + " Imbrication:" + imbrication, " Déplacement");
        // asciiTable.addRule();

        // for (Symbol symbol : symbols) {
        //     asciiTable.addRow(symbol, symbol.getDeplacement());
        // }
        // if (!symbols.isEmpty())
        //     asciiTable.addRule();

        // asciiTable.setTextAlignment(TextAlignment.CENTER);
        // return asciiTable.render();

        AsciiTable asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("TDS" + this.name, " nom du symbole", " type du symbole", " déplacement");

        for (Symbol symbol : symbols) {
            asciiTable.addRule();
            asciiTable.addRow("Symbol", symbol.getName(), symbol.getType(), symbol.getDeplacement());
        }

        for (Tds tds : this.child) {
            System.out.println(tds.toString());
        }

        System.out.println("\n");

        return asciiTable.render(); 
    }
}
