package lotus.domino.axis;
import lotus.domino.*;

public class JavaAgentRenamed extends AgentBase {
    // I'm Library JavaAgentRenamed

    public void NotesMain() {

      try {
          Session session = getSession();
          AgentContext agentContext = session.getAgentContext();

          // (Your code goes here)

      } catch(Exception e) {
          e.printStackTrace();
       }
   }
}