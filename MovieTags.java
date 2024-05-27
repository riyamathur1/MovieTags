import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieTags {
	public static void main(String[] args) {
		System.out.println("Reading data file .....");
		
		String fileName = args[0];
        int rowNumber = 0;
		
		// create new Tag object and add to ArrayList for every row
        List<Tag> tagList = new ArrayList<>();
		BufferedReader br = null;

        try {
     		// creating file reader
			br = new BufferedReader(new FileReader(fileName));
			String line;
            while ((line = br.readLine()) != null) {
                if (rowNumber != 0) { // skip the header row
                    String[] data = new String[4];

					// not using split because some tags have commas within them
					int comma = line.indexOf(',');
					data[0] = line.substring(0, comma);

					int comma2 = line.indexOf(',', comma + 1);
					data[1] = line.substring(comma+1, comma2);

					int comma3 = line.lastIndexOf(',');
					data[2] = line.substring(comma2 + 1, comma3);
					data[3] = line.substring(comma3 + 1);

                    Tag tag = new Tag(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], Long.parseLong(data[3]));
                    tagList.add(tag);
                }
                rowNumber++;
            }
			br.close(); // close the reader
        } catch (IOException e) {
            e.printStackTrace();
        }

		// sort the list of tags by tag name
		mergeSort(tagList, 0, tagList.size() - 1);

		List<TagFrequency> frequencies = new ArrayList<>();

		// initialize the first frequency with the first tag in the sorted list
		TagFrequency currentFrequency = new TagFrequency(tagList.get(0).getTag(), 1);

		for (int i = 1; i < tagList.size(); i++) {
			Tag tag = tagList.get(i);

			if (tag.getTag().equals(currentFrequency.getTag())) {
				currentFrequency.incrementCount();
			} else {
				frequencies.add(currentFrequency);
				currentFrequency = new TagFrequency(tag.getTag(), 1);
			}
		}
		// add the last frequency to the list
		frequencies.add(currentFrequency);

		// sort frequencies by count and break ties by name
		mergeSortFreq(frequencies, 0, frequencies.size() - 1);

		System.out.println(" ==========================================");
		System.out.println("*** Highest 3 movies by count ***");
		List<TagFrequency> highest = frequencies.subList(0, 3);
		for (TagFrequency freq : highest) {
			System.out.println(freq.getCount() + ": " + freq.getTag());
		}
		
		System.out.println("*** Lowest 3 movies by count ***");
		List<TagFrequency> lowest = frequencies.subList(frequencies.size() - 4, frequencies.size() - 1);
		for (TagFrequency freq : lowest) {
			System.out.println(freq.getCount() + ": " + freq.getTag());
		}
		System.out.println(" ==========================================");

		// create deep copy of list (this one will be sorted by name rather than count)
		List<TagFrequency> frequenciesByName = new ArrayList<>(frequencies);
		mergeSortFreqName(frequenciesByName, 0, frequenciesByName.size() - 1);

		// begin user input phase
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.print("Search by Tag or Tag Count? (Enter T or C... or EXIT to exit): ");
			String choice = input.nextLine();
			if (choice.toUpperCase().equals("EXIT")) {
				break;
			}
			if (choice.toUpperCase().equals("T")) {
				System.out.print("Tag to search for: ");
				String tag = input.nextLine();
				// SEARCH HERE
				if (binarySearchTag(0, frequenciesByName.size()-1, tag, frequenciesByName) == -1) {
					System.out.println("Tag " + tag + " does not exist.");
				}
				else {
					System.out.println("Tag " + tag + " occurred " + binarySearchTag(0, frequenciesByName.size()-1, tag, frequenciesByName) + " times");
				}

			} else if (choice.toUpperCase().equals("C")) {
				System.out.print("Count to search for: ");
				String count = input.nextLine();
				if (isNumeric(count) == false) {
					System.out.println("Is " + count + " even a number? C'mon man!");
				} else {
					// SEARCH HERE
					System.out.println("Tags with " + count + " occurrences: ");
					ArrayList<String> result = binarySearchCount(0, frequencies.size()-1, count, frequencies);
					for (int i = 0; i < result.size()-1; i++) {
						System.out.println(result.get(i));
					}
				}
			} else {
				System.out.println("Please enter T, C, or EXIT!");
			}
		}
		System.out.println("Bye!");
		input.close();
    }
    
    
    public static ArrayList<String> binarySearchCount(int low, int high, String count, List<TagFrequency> frequencies) {
    	ArrayList<String> results = new ArrayList<String>();
    	while (low <= high) {
	        int mid = (low + high) / 2;

	        if (frequencies.get(mid).getCount() < Integer.parseInt(count)) {
	            high = mid - 1;
	        } 
	        else if (frequencies.get(mid).getCount() > Integer.parseInt(count)) {
	            low = mid + 1;
	        } 
	        else {
	            int idx = mid;
	            int freq_size = frequencies.size();
	            while ((idx < freq_size) && (frequencies.get(idx).getCount() == Integer.parseInt(count))) {
	            	results.add(frequencies.get(idx).getTag());
	   				idx++;
	            }
	            idx = mid;
	           	while ((idx >= 0) && (frequencies.get(idx).getCount() == Integer.parseInt(count))) {
	            	results.add(frequencies.get(idx).getTag());
	   				idx--;
	            }
	            break;
	        }
    	}
    	return results;
    }


    public static int binarySearchTag(int low, int high, String tag, List<TagFrequency> frequenciesByName) {
    	while (low <= high) {
	        int mid = (low + high) / 2;

	        if (frequenciesByName.get(mid).getTag().compareTo(tag) < 0) {
	            low = mid + 1;
	        } 
	        else if (frequenciesByName.get(mid).getTag().compareTo(tag) > 0) {
	            high = mid - 1;
	        } 
	        else {
	            // Found the tag, return the corresponding value
	            return frequenciesByName.get(mid).getCount();
	        }
    	}
    	return -1;
	}

	public static boolean isNumeric(String str) { 
		try {  
		  Integer.parseInt(str);  
		  return true;
		} catch(NumberFormatException e){  
		  return false;  
		}  
	}

	public static void mergeSort(List<Tag> tagList, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergeSort(tagList, low, mid);
			mergeSort(tagList, mid + 1, high);
			merge(tagList, low, mid, high);
		}
	}
	
	// merge sort algorithm
	public static void merge(List<Tag> tagList, int low, int mid, int high) {
		int leftSize = mid - low + 1;
		int rightSize = high - mid;
	
		List<Tag> leftList = new ArrayList<>();
		List<Tag> rightList = new ArrayList<>();
	
		for (int i = 0; i < leftSize; i++) {
			leftList.add(tagList.get(low + i));
		}
		for (int j = 0; j < rightSize; j++) {
			rightList.add(tagList.get(mid + 1 + j));
		}
	
		int i = 0;
		int j = 0;
		int k = low;
	
		while (i < leftSize && j < rightSize) {
			if (leftList.get(i).getTag().compareTo(rightList.get(j).getTag()) <= 0) {
				tagList.set(k, leftList.get(i));
				i++;
			} else {
				tagList.set(k, rightList.get(j));
				j++;
			}
			k++;
		}
	
		while (i < leftSize) {
			tagList.set(k, leftList.get(i));
			i++;
			k++;
		}
	
		while (j < rightSize) {
			tagList.set(k, rightList.get(j));
			j++;
			k++;
		}
	}

	public static void mergeSortFreq(List<TagFrequency> frequencies, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergeSortFreq(frequencies, low, mid);
			mergeSortFreq(frequencies, mid + 1, high);
			mergeFreq(frequencies, low, mid, high);
		}
	}
	
	public static void mergeFreq(List<TagFrequency> frequencies, int low, int mid, int high) {
		int leftSize = mid - low + 1;
		int rightSize = high - mid;
	
		List<TagFrequency> leftList = new ArrayList<>();
		List<TagFrequency> rightList = new ArrayList<>();
	
		for (int i = 0; i < leftSize; i++) {
			leftList.add(frequencies.get(low + i));
		}
		for (int j = 0; j < rightSize; j++) {
			rightList.add(frequencies.get(mid + 1 + j));
		}
	
		int i = 0;
		int j = 0;
		int k = low;
	
		while (i < leftSize && j < rightSize) {
			if (leftList.get(i).getCount() > rightList.get(j).getCount() || 
					(leftList.get(i).getCount() == rightList.get(j).getCount() && 
					 leftList.get(i).getTag().compareTo(rightList.get(j).getTag()) < 0)) {
				frequencies.set(k, leftList.get(i));
				i++;
			} else {
				frequencies.set(k, rightList.get(j));
				j++;
			}
			k++;
		}
	
		while (i < leftSize) {
			frequencies.set(k, leftList.get(i));
			i++;
			k++;
		}
	
		while (j < rightSize) {
			frequencies.set(k, rightList.get(j));
			j++;
			k++;
		}
	}

	public static void mergeSortFreqName(List<TagFrequency> frequencies, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergeSortFreqName(frequencies, low, mid);
			mergeSortFreqName(frequencies, mid + 1, high);
			mergeFreqName(frequencies, low, mid, high);
		}
	}
	
	public static void mergeFreqName(List<TagFrequency> frequencies, int low, int mid, int high) {
		int leftSize = mid - low + 1;
		int rightSize = high - mid;
	
		List<TagFrequency> leftList = new ArrayList<>();
		List<TagFrequency> rightList = new ArrayList<>();
	
		for (int i = 0; i < leftSize; i++) {
			leftList.add(frequencies.get(low + i));
		}
		for (int j = 0; j < rightSize; j++) {
			rightList.add(frequencies.get(mid + 1 + j));
		}
	
		int i = 0;
		int j = 0;
		int k = low;
	
		while (i < leftSize && j < rightSize) {
			if (leftList.get(i).getTag().compareTo(rightList.get(j).getTag()) < 0) {
				frequencies.set(k, leftList.get(i));
				i++;
			}	else {
				frequencies.set(k, rightList.get(j));
				j++;
			}
			k++;
		}
	
		while (i < leftSize) {
			frequencies.set(k, leftList.get(i));
			i++;
			k++;
		}
	
		while (j < rightSize) {
			frequencies.set(k, rightList.get(j));
			j++;
			k++;
		}
	}
}






