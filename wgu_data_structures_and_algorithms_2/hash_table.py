# In order to distinguish elements in each
# bucket, the key must be stored along with
# each value.
class HashTableElement():
    def __init__(self, key, value):
        self.key = key
        self.value = value

# An iterator class that holds a HashTable
# and the current location in the table.
class HashTableIterator():
    def __init__(self, hash_table, type="values"):
        self.hash_table = hash_table
        self.current_bucket = 0
        self.current_bucket_index = 0
        self.type = type
    
    # the iterator for this class is itself
    def __iter__(self):
        return self

    # returns the next value in the hashtable
    def __next__(self):
        # find the next non-empty bucket
        while  self.current_bucket < len(self.hash_table.table) and len(self.hash_table.table[self.current_bucket]) == 0:
            self.current_bucket = self.current_bucket + 1
            self.current_bucket_index = 0

        # stop iterating if we've searched through the entire hashtable
        if self.current_bucket >= len(self.hash_table.table):
            raise StopIteration

        # grab the HashTableElement to return
        to_ret = self.hash_table.table[self.current_bucket][self.current_bucket_index]

        # set the next index of the next element to return
        self.current_bucket_index = self.current_bucket_index + 1

        # check that the index of the next element is within bounds 
        if (self.current_bucket_index >= len(self.hash_table.table[self.current_bucket])):
            self.current_bucket = self.current_bucket + 1
            self.current_bucket_index = 0

        # modify what to return based on what type of iterator this is
        if self.type == "values":
            return to_ret.value
        elif self.type == "items":
            return to_ret.key, to_ret.value
        else:
            return to_ret.key

# a chaining Hash Table implementation
class HashTable():
    def __init__(self, init_size=11):
        self.table = []
        for i in range(init_size):
            self.table.append([])
        self.num_elements = 0

    # insert an item into this HashTable
    def put(self, key, value):
        # remove the duplicate key
        if self.get(key) != None:
            self.remove(key)
            
        # create a HashTableElement which holds a key and value
        to_insert = HashTableElement(key, value)

        # get the array index to insert this value into
        index = hash(key) % len(self.table)


        # insert this value at the appropriate index
        self.table[index].append(to_insert)
        self.num_elements += 1

        self.check_for_resize()

    # Retrieve an item from this HashTable
    def get(self, key):
        # get the array index where this key's item should be held
        index = hash(key) % len(self.table)

        # get the list at this index
        list_at_index = self.table[index]

        # return the item if it exists
        for htElement in list_at_index:
            if htElement.key == key:
                return htElement.value
        
        # else return None
        return None

    # Remove an item from this HashTable
    def remove(self, key):
        # get the array index where this key's item should be held
        index = hash(key) % len(self.table)

        # get the list at this index
        list_at_index = self.table[index]

        # remove the item if it exists
        for htElement in list_at_index:
            if htElement.key == key:
                list_at_index.remove(htElement)
                self.num_elements -= 1
                return
    
    # Resize this HashTable
    def resize(self, new_size):
        if new_size < 1:
            return
        new_table = []
        for i in range(new_size):
            new_table.append([])

        old_table = self.table
        self.table = new_table
        for bucket in old_table:
            for item in bucket:
                self.put(item.key, item.value)

    # resize the hashtable if the number of elements
    # being stored is greater than 0.8 of the table's
    # size. Change its size to be 1.7 times greater.
    def check_for_resize(self):
        if self.num_elements > 0.8 * len(self.table):
            self.resize(int(len(self.table) * 1.7))
        
    # Return a HashTableIterator that will return values
    def __iter__(self):
        return HashTableIterator(self)

    # Return a HashTableIterator that returns a tuple (key, value)
    def items(self):
        return HashTableIterator(self, type="items")

    # Return a HashTableIterator that returns keys
    def keys(self):
        return HashTableIterator(self, type="keys")

    # Returns a HashTableIterator that will return values
    def values(self):
        return self.__iter__()

    
def test():
    hashTable = HashTable()
    hashTable.put("My name is", "Slim Shady")
    anotherKey = ("string one", 500)
    hashTable.put(anotherKey, 400)
    print(hashTable.get("My name is"))
    print(hashTable.get(anotherKey))

    hashTable.remove("My name is")
    print(hashTable.get("My name is"))
    print(hashTable.get(anotherKey))
    
    hashTable.remove(anotherKey)
    print(hashTable.get("My name is"))
    print(hashTable.get(anotherKey))

    hashTable.put("My name is", "Slim Shady")
    hashTable.put(anotherKey, 400)
    print(hashTable.get("My name is"))
    print(hashTable.get(anotherKey))

    print(f'size: {len(hashTable.table)}')
    print("resizing to 31")
    hashTable.resize(31)
    print(f'size: {len(hashTable.table)}')
    
    print(hashTable.get("My name is"))
    print(hashTable.get(anotherKey))
    

if __name__ == "__main__":
    test()