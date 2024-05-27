# Tag Analysis Project

## Project Description
In this project, I created a search functionality for movie tags from the MovieLens Dataset. The goal is to analyze the tags used by entertainment consumers, leveraging efficient data structures and algorithms. The implementation reads data from a file of tags, allows the user to search for individual tags by popularity, and displays the results in a user-friendly manner.

## Data Structures and Algorithms

### Data Structures Used
- **Tag**: A class for holding the attributes of each row of the CSV.
- **ArrayList<Tag>**: Holds a `Tag` for each row in the CSV.

### Algorithm: Parsing CSV
For each line in the CSV:
1. Find the 3 delimiting commas that separate the attributes of a row.
2. Construct a `Tag` instance from those attributes.
3. Append the `Tag` instance to an `ArrayList<Tag>`.

**Big-O Running Time**: `O(N)` where `N` is the number of rows in the CSV.

---

### List Most and Least Popular Tags

#### Data Structures Used
- **TagFrequency**: A class that holds the tag and its frequency.
- **ArrayList<TagFrequency>**: Holds `TagFrequency` instances for unique tags.
- **ArrayList<TagFrequency> sublists**: Holds the most and least frequent tags.

#### Algorithm
1. Sort the `tags` list by name.
2. Create a new `ArrayList<TagFrequency> frequencies`.
3. Create a `TagFrequency` instance for the first tag in the `tags` list.
4. Iterate over the `tags` list:
   - Increment the frequency of the current `TagFrequency` object if the tag matches.
   - Otherwise, append the current `TagFrequency` object to `frequencies` and create a new `TagFrequency` object for the new tag.
5. Sort `frequencies` by count.
6. Create sublists with the first 3 objects (highest frequency) and last 3 objects (lowest frequency).
7. Print these sublists.

**Runtime**:
- Sorting `tags` list by names: `O(N log N)`
- Creating the `frequencies` list: `O(N)`
- Sorting `frequencies` by count: `O(N log N)`
- Creating sublists: `O(1)`

**Final Runtime**: `O(N log N) + O(N) + O(N log N) + O(1) = O(N log N)`

---

### Find Tags by Count and Counts by Name

#### Data Structures Used
- **ArrayList<TagFrequency> frequenciesByName**: Sorted by tag name.
- **ArrayList<TagFrequency> frequencies**: Sorted by count.
- **ArrayList<TagFrequency> results**: Stores the tags with matching frequencies for output.

#### Algorithm
1. Sort `frequencies` by tag name and store in `frequenciesByName`.
2. Use the sorted `frequencies` by count.

**If searching by tag**:
- Perform a binary search on `frequenciesByName` for the given tag.
- Print the tag’s frequency if found; otherwise, print that the tag wasn’t found.

**If searching by count**:
- Validate the given count input.
- Perform a binary search in `frequencies` for an index (`idx`) with the given count.
- Search indices to the left and right of `idx` for tags with the matching count.
- Return the tags with the matching count.

**Runtimes**:
- Sorting the lists: `O(N log N)`
- Searching `frequencies`: `O(log N)`
- Searching `frequenciesByName`: `O(log N) + O(N)`

**Final Runtime**:
- Initial sorting cost: `O(N log N)`
- Repeated search cost: `O(log N)`
- Worst case for count search: `O(N)`

---
