package app.entity;


public class AdvanceCommand extends Commands {

    int advanceSteps;

    public AdvanceCommand(int advanceSteps) {
        super();
        this.advanceSteps = advanceSteps;

    }

    public int getAdvanceSteps() {
        return advanceSteps;
    }

    @Override
    public String toString() {
        return "advance " + advanceSteps;
    }
}
