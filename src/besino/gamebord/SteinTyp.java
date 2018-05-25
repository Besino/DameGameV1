package besino.gamebord;

public enum SteinTyp {
    WEISS(1), SCHWARZ(-1), DAMEWEISS(0), DAMESCHWARZ(0);

    final int moveDir;

    SteinTyp(int moveDir){
        this.moveDir = moveDir;
    }

}
