import org.junit.jupiter.api.Test;
import org.pcl.structure.automaton.AutomatonState;
import org.pcl.structure.automaton.IncorrectAutomatonException;
import org.pcl.structure.automaton.InvalidStateExeception;
import org.pcl.structure.automaton.TokenType;
import org.pcl.structure.automaton.Automaton;

import static org.junit.jupiter.api.Assertions.assertThrows;

/** Test the automaton classes */
public class AutomatonIntegrityTest {

    /** Verify exception are thrown when they should */
    @Test
    public void automatonStateDeterminisicTest() {
        AutomatonState state = new AutomatonState('=', false);
        AutomatonState state1 = new AutomatonState('1', false);
        AutomatonState state2 = new AutomatonState('1', false);
        state.addAdjacent(state1);
        assertThrows(IncorrectAutomatonException.class, () -> {
            state.addAdjacent(state2); // This should throw a IncorrectAutomatonException
        });

        state1.addAdjacent(state2);
        state.naviguate('1').naviguate('1');
        assertThrows(InvalidStateExeception.class, () -> state.naviguate('1').naviguate('2'));
    }

    /** verify that the link are correct */
    @Test
    public void automatonStateFinalTest() {
        AutomatonState state = new AutomatonState('=', true);
        AutomatonState state11 = new AutomatonState('+', true);
        AutomatonState state12 = new AutomatonState('-', true);
        AutomatonState state2 = new AutomatonState('2', true);
        AutomatonState state3 = new AutomatonState('3', true);
        AutomatonState state4 = new AutomatonState('4', true);

        state.addAdjacent(state11);
        state.addAdjacent(state12);
        state11.addAdjacent(state2);
        state2.addAdjacent(state3);
        state3.addAdjacent(state4);

        state.naviguate('+').naviguate('2').naviguate('3').naviguate('4');
        state.naviguate('-');
        state11.naviguate('2').naviguate('3').naviguate('4');
        state3.naviguate('4');
    }

    /** Verify advance function */
    @Test
    public void advanceAutomatonTest(){
        
        AutomatonState stateA = new AutomatonState('a', false);
        AutomatonState stateB = new AutomatonState('b', true, TokenType.KEYWORD);
        AutomatonState stateC = new AutomatonState('c', false);
        AutomatonState stateE = new AutomatonState('e', true, TokenType.IDENTIFIER);

        stateA.addAdjacent(stateB);
        stateB.addAdjacent(stateC);
        stateC.addAdjacent(stateE);

        Automaton automaton = new Automaton(stateA);

        automaton.advance('b');
        assert automaton.isFinal();
        assert automaton.getCurrentState().getTokenType() == TokenType.KEYWORD;

        automaton.advance('c');
        assert !automaton.isFinal();
        assert automaton.getCurrentState().getTokenType() == null;

        automaton.advance('e');
        assert automaton.isFinal();
        assert automaton.getCurrentState().getTokenType() == TokenType.IDENTIFIER;    
    }

}
