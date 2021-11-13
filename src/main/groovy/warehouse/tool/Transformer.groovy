package warehouse.tool

/*
 * Class used as a utility tool
 * I had to reduce the number of entries below 10k, because of limitations of my free cloud DB instance
 */

class Transformer {

    static void main(String[] args) {

        def writer = new FileWriter('src/main/resources/cropped.csv')
        def i = 0

        new File('src/main/resources/input.csv').eachLine {line ->
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
