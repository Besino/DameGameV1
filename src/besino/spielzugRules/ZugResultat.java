package besino.spielzugRules;

import besino.gamebord.Spielstein;

public class ZugResultat {
    private ZugTyp zugTyp;
    private Spielstein spielstein;

    public ZugResultat(ZugTyp zugTyp, Spielstein spielstein){
        this.zugTyp = zugTyp;
        this.spielstein = spielstein;
    }

    public ZugResultat(ZugTyp zugTyp){
        this(zugTyp, null);
    }

    public ZugTyp getZugTyp(){
        return zugTyp;
    }

    public Spielstein getSpielstein(){
        return spielstein;
    }
}
