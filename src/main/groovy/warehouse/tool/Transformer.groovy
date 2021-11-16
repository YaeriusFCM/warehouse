package warehouse.tool

/**
 * @author p.dobrzanski@yaerius.eu
 * Class used as a utility tool
 * Normally I would create a separated script for this, but this time I wanted to include it in the project
 * I had to reduce the number of entries below 10k, because of limitations of my free cloud DB instance
 */

class Transformer {

    static final String INPUT_FILE = 'src/main/resources/input.csv'
    static final String OUTPUT_FILE = 'src/main/resources/cropped.csv'

    static void main(String[] args) {

        def writer = new FileWriter(OUTPUT_FILE)
        def i = 0

        new File(INPUT_FILE).eachLine {line ->
            if (i % 3 == 0) {
                writer.println line
            }
            i++
        }
        writer.flush()
        writer.close()

        print 'DONE'
    }
}
