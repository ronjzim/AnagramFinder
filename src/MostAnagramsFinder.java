import java.io.*;
import java.util.Arrays; 
import java.util.Iterator;

public class MostAnagramsFinder {

    /**
     * Insertion Sort for character arrays. Modified from class version
     * @param array to be sorted in lexicographical order
     */
    public static void insertionSort(char[] array) {
        for (int i = 1; i < array.length; i++) {
            int k;
            char current = array[i];
            for (k = i - 1; k >= 0 && array[k] > current; --k) {
                array[k + 1] = array[k];
            }
            array[k + 1] = current;
        }
    }

    /**
     * Insertion Sort algorithm for String arrays. Modified from class version
     * @param array to be sorted in lexicographical order
     */
    public static void insertionSort(String[] array) {
        for (int i = 1; i < array.length; i++) {
            int k;
            String current = array[i];
            for (k = i - 1; k >= 0 && array[k].compareTo(current) > 0; --k) {
                array[k + 1] = array[k];
            }
            array[k + 1] = current;
        }
    }

    /**
     * Insertion Sort algorithm for arrays of String arrays. Modified from class version
     * @param array of string arrays to be sorted in lexicographical order according to the first word
     *              in each array.
     */
    public static void insertionSort(String[][] array) {
        for (int i = 1; i < array.length; i++) {
            int k;
            String[] current = array[i];
            for (k = i - 1; k >= 0 && compareStringArray(array[k], current) > 0; --k) {
                array[k + 1] = array[k];
            }
            array[k + 1] = current;
        }
    }

    /**
     * A comparison method to compare the first word of two String arrays lexicographically.
     * @param array1 the first string array to compare.
     * @param array2 the second string array to compare.
     * @return an int used to determine the correct ordering of the two string arrays.
     */
    public static int compareStringArray(String[] array1, String[] array2) {
        if (array1[0].compareTo(array2[0]) > 0) {
            return 1;
        } else if (array1[0].compareTo(array2[0]) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Fills a MyMap with lowercase keys of all words in the input file and MyList's of all the anagrams
     * of those keys found in the input file
     * @param map the MyMap object to add to
     * @param reader the BufferedReader attached to the input file.
     * @throws IOException In case the BufferedReader runs into a problem reading a line in the file.
     */
    public static void mapFill(MyMap<String, MyList<String>> map, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null ) {
            char[] lineChars = line.toCharArray();
            for (int j = 0; j < lineChars.length; j++) {
                lineChars[j] = Character.toLowerCase(lineChars[j]);
            }
            insertionSort(lineChars);
            String key = String.valueOf(lineChars);
            if (map.get(key) == null) {
                MyList<String> values = new MyLinkedList<>();
                values.add(line);
                map.put(key, values);
            } else {
                MyList<String> values = map.get(key);
                Boolean wordExists = false;
                for (int i = 0; i < values.size(); i++) {
                    if (values.get(i).equalsIgnoreCase(line)) {
                        wordExists = true;
                        break;
                    }
                }
                if (!wordExists) {
                    values.add(line);
                }
            }
        }
    }

    /**
     * Takes in a MyMap, the anagramCount int which tracks the most amount of anagrams found in the file,
     * And the groupCount which is how many groups are to be inserted into this new array.
     * @param map to iterate through and check for matching anagram counts
     * @param anagramCount the max amount of anagrams for any given word found in the MyMap
     * @param groupCount the amount of groups who have that anagramCount, size of the array to be returned.
     * @return A MyList of Strings array containing all the groups of MyLists of size anagramCount
     * in the MyMap.
     */
    private static MyList<String>[] mapToAnagramArray(MyMap<String, MyList<String>> map, int anagramCount, int groupCount) {
        MyList<String>[] groupsArray = new MyLinkedList[groupCount];
        Iterator<Entry<String, MyList<String>>> iterator2 = map.iterator();
        int i = 0;
        while (iterator2.hasNext()) {
            MyList<String> mapValue = iterator2.next().value;
            if (mapValue.size() == anagramCount) {
                groupsArray[i] = mapValue;
                i++;
            }
        }
        return groupsArray;
    }

    /**
     * Takes in a MyList of Strings and converts it into an array of strings.
     * @param list to take in and place each element into the new String array.
     * @return The new String array.
     */
    private static String[] listToStringArray(MyLinkedList<String> list) {
        String[] stringArray = new String[list.size()];
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = list.get(i);
        }
        insertionSort(stringArray);
        return stringArray;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java MostAnagramsFinder <dictionary file> <bst|rbt|hash>");
            System.exit(1);
        }
        try {
            FileReader fr = new FileReader(args[0]);
            BufferedReader reader = new BufferedReader(fr);
            if (!(args[1].equals("bst")) && !(args[1].equals("rbt")) && !(args[1].equals("hash"))) {
                System.err.println("Error: Invalid data structure '" + args[1] + "' received.");
                System.exit(1);
            }
            MyMap<String, MyList<String>> map;
            if (args[1].equals("bst")) {
                map = new BSTreeMap<>();
                mapFill(map, reader);
            } else if (args[1].equals("rbt")) {
                map = new RBTreeMap<>();
                mapFill(map, reader);
            } else {
                map = new MyHashMap<>();
                mapFill(map, reader);
            }
            reader.close();
            Iterator<Entry<String, MyList<String>>> iterator = map.iterator();
            MyList<String> currentMax = iterator.hasNext()? iterator.next().value : null;
            int anagramCount = (currentMax != null) ? currentMax.size() : 0;
            int groupCount = (currentMax != null && anagramCount > 0) ? 1 : 0;
            while (iterator.hasNext()) {
                MyList<String> newList = iterator.next().value;
                if (newList != null) {
                    int newSize = newList.size();
                    if (newSize > anagramCount) {
                        anagramCount = newSize;
                        groupCount = 1;
                    } else if (newSize == anagramCount && anagramCount > 1) {
                        groupCount++;
                    }
                }
            }
            if (anagramCount <= 1) {
                System.out.println("No anagrams found.");
            } else {
                MyLinkedList<String>[] groupsListArray = (MyLinkedList<String>[]) mapToAnagramArray(map, anagramCount, groupCount);
                String[][] groupsArray = new String[groupsListArray.length][];
                for (int i = 0; i < groupsListArray.length; i++) {
                    groupsArray[i] = listToStringArray(groupsListArray[i]);
                }
                insertionSort(groupsArray);
                System.out.println("Groups: " + groupCount + ", Anagram count: " + anagramCount);
                for (String[] group : groupsArray) {
                    System.out.println(Arrays.toString(group));
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("Error: Cannot open file '" + args[0] + "' for input.");
            System.exit(1);
        } catch (IOException IOe) {
            System.err.println("Error: An I/O error occurred reading '" + args[0] + "'.");
            System.exit(1);
        }
    }

}