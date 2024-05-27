
Data structures used: Tag, a class for holding the attributes of each row of the csv and ArrayList which holds a Tag for each row in the csv.

Algorithm:
For each line in the csv
- Find the 3 delimiting commas that separated the attributes of a row
- Construct a Tag instance from those attributes
- Append those to an ArrayList of tags.

Big-O running time: O(N) where N is the number of rows in the csv.

		 	 	 							
List most and least popular tags:
 
Data structures used: 
- TagFrequency, a class that holds the tag and frequency for each row of the csv, created in the previous step.
- ArrayList frequencies which hold the TagFrequency’s for unique tags.
- Lowest and highest which are both Arraylist sublists of frequencies.

Algorithm:
- Sort the tags list by name. 
- Create a new ArrayList, frequencies, which will hold TagFrequency instances. 
- Create a TagFrequency instance for the first tag in the tags list. 
- Iterate over the tags list 
- Keep incrementing the frequency of the current TagFrequency object if the current TagFrequency object’s tag and the current word being iterated over are the same. 
- Otherwise append the current TagFrequencies object to our frequencies list and create a new TagFrequency object for the new unique tag. Go back to d. and treat the new TagFrequency object as the current TagFrequency object.
- Sort the frequencies by count.
- Create sublists with the first 3 objects in the frequencies list (highest frequency) and last 3 objects (lowers frequency). Print these in the proper format for presenting highest and lowest frequency.

Runtime: 
O(N Log N) for sorting tags list by names
O(N) for creating the frequencies list
O(N Log N) for sorting the frequencies by count, where N is the number of unique tags
Creating sublists of size 3 is a constant time operation O(1)
Final runtime: O (N Log N)  + O(N) +  O (N Log N) + O(1) = O (N Log N)

Find tags by count and counts by name

Data structures used: 
- frequenciesByName and frequencies are ArrayLists with N possible elements where N is the number of rows in tags.csv. 
- TagFrequency, a class that holds the tag and frequency for each row of the csv, created in the previous step.
- Results ArrayList for storing the tags that have matching frequencies to for printing to console.

Algorithm:
- Sort the frequencies by unique tag name and store in frequenciesByName
- Use the sorted frequencies by count stored in frequencies

If searching by tag:
Do a binary search on frequenciesByName for the given tag. If the given tag exists, print that tag’s frequency, otherwise print that the tag wasn’t found.

If searching by count:
Do error handling for the given count input.
Do a binary search for an index, idx, in the frequencies list that has the given count.
Search indices in the frequencies list to the left and right of idx for tags that have matching count to the given count. This allows us to capture all of the tags that may have the same count.
Return those tags with matching count.

Runtimes:
N is the number of unique tags.
Sorting the frequencies and frequenciesByName lists: O (N Log N).
Searching the frequencies: O (log N) where N is the number of unique tags.
Searching the frequenciesByName list: O(Log N) + O (N)
Final runtime: The initial cost to sort is N Log N, however the repeated cost of searching for a frequency is Log N. The repeated cost of searching for a count is more complicated since in most cases it will turn out to be closer to that of a binary search, Log N, however in the worst case where most if not all elements have the same frequency, it will work out to O (N), because of the linear search that is required to find tags with identical frequency.
						 			
