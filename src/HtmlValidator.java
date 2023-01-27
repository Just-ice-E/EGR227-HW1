/**
 * This code was done in collaboration with Christopher Trafton (Also in EGR227-A)
 */

import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static java.lang.System.*;

public class HtmlValidator {
    public Queue<HtmlTag> q = new LinkedList<>();

    //What even does this do, I'm not sure but the error went away
    public HtmlValidator() {
    }

    //Method to test for null tag being null
    public HtmlValidator(Queue<HtmlTag> tags) {
        if (tags.toString() == null) throw new IllegalArgumentException();
        q = tags;
    }

    //Tests for null input queue
    public void addTag(HtmlTag tag) {
        if (tag == null) throw new IllegalArgumentException();

        //Adds another value to the queue?
        q.add(tag);
    }

    //Returns the vaules that are in q the queue
    public Queue<HtmlTag> getTags() {
        return q;
    }

    //Tests to see if element is null
    public void removeAll(String element) {
        if (element == null) throw new IllegalArgumentException();

        HtmlTag value;
        for (int i = 0; i < q.size(); i++) {
            value = q.peek();
            q.remove();
            if (value.toString() != element) q.add(value);
        }
    }

    public void validate() {
        //Initializes stack and creates variable to validate stack is not empty
        Stack<HtmlTag> s = new Stack<>();
        HtmlTag value;
        int indent = 0;
        int size = q.size();

        //Tests to make sure the queue is not empty
        if (q.size() == 0) {
            System.out.print("");
            return;
        }

        //Goes through the entire queue
        for (int i = 0; i < size; i++) {
            value = q.peek();
            q.remove();

            if (value.getElement() == "/br") {
                System.out.println("Error unexpected tag: " + value);

                //checks for the br case because it is self-closing
            } else if (value.isOpenTag() && !value.isSelfClosing()) {
                s.push(value);
                for (int k = 0; k < indent; k++) {
                    System.out.print("     ");
                }
                System.out.println(value);
                indent++;
            } else if (value.isSelfClosing()) {
                for (int k = 0; k < indent; i++) {
                    System.out.print("    ");
                }
                System.out.println(value);
            } else {
                //un-does an increment of the indentation
                if (!s.isEmpty()) {
                    if (value.matches(s.peek())) {
                        s.pop();
                        indent--;
                        for (int k = 0; k < indent; k++) {
                            System.out.print("    ");
                        }
                        System.out.println(value);
                    } else if (!value.isOpenTag() || !value.isSelfClosing()) {
                        System.out.println("Error, unexpected tag: " + value);
                    }
                } else {
                    System.out.println("Error, unexpected tag: " + value);
                }
            }
        }
        while (!s.isEmpty()) {
            System.out.println("Error, unexpected tag: " + s.peek());
            s.pop();
        }
    }
}
