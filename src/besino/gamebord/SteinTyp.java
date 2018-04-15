package besino.gamebord;

public enum SteinTyp {
    WEISS(1), SCHWARZ(-1), DAMEWEISS(1), DAMESCHWARZ(-1);

    final int moveDir;

    SteinTyp(int moveDir){
        this.moveDir = moveDir;
    }

}
