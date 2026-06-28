package class1.s4_typecasting;

/**
 * TypeCastingDemo — Widening (implicit) and Narrowing (explicit) type casting.
 *
 * Widening: smaller type → larger type (automatic, no data loss)
 * Narrowing: larger type → smaller type (manual, possible data loss)
 */
public class TypeCastingDemo {

    public static void main(String[] args) {

        // -----------------------------------------------
        // WIDENING — Java does this automatically
        // -----------------------------------------------
        int   intValue    = 150;
        long  longValue   = intValue;   // int → long (auto)
        float floatValue  = longValue;  // long → float (auto)
        double doubleValue = floatValue; // float → double (auto)

        System.out.println("--- Widening (Automatic) ---");
        System.out.println("int:    " + intValue);
        System.out.println("long:   " + longValue);
        System.out.println("float:  " + floatValue);
        System.out.println("double: " + doubleValue);

        // -----------------------------------------------
        // NARROWING — must cast manually with (type)
        // -----------------------------------------------
        double price    = 99.95;
        int    rounded  = (int) price;  // decimal part is DROPPED (not rounded)

        System.out.println("\n--- Narrowing (Manual Cast) ---");
        System.out.println("double: " + price);
        System.out.println("int:    " + rounded);  // 99, not 100

        // Another example: int → byte (overflow risk)
        int bigInt  = 300;
        byte b      = (byte) bigInt;  // 300 overflows byte range (-128 to 127)

        System.out.println("\n--- Overflow Example ---");
        System.out.println("int 300 cast to byte: " + b); // unexpected value!
    }
}
