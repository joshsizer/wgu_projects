# In order to distinguish elements in each
# bucket, the key must be stored along with
# each value.
class HashTableElement():
    def __init__(self, key, value):
        self.key = key
        self.value = value

class HashTableIterator():
    def __init__(self, hash_table, type="values"):
        self.hash_table = hash_table
        self.current_bucket = 0
        self.current_bucket_index = 0
        self.type = type

    def __iter__(self):
        return self

    def __next__(self):
        while  self.current_bucket < len(self.hash_table.table) and len(self.hash_table.table[self.current_bucket]) == 0:
            self.current_bucket = self.current_bucket + 1
            self.current_bucket_index = 0

        if self.current_bucket >= len(self.hash_table.table):
            raise StopIteration

        to_ret = self.hash_table.table[self.current_bucket][self.current_bucket_index]
        self.current_bucket_index = self.current_bucket_index + 1
        if (self.current_bucket_index >= len(self.hash_table.table[self.current_bucket])):
            self.current_bucket = self.current_bucket + 1
            self.current_bucket_index = 0

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

    def remove(self, key):
        # get the array index where this key's item should be held
        index = hash(key) % len(self.table)

        # get the list at this index
        list_at_index = self.table[index]

        # remove the item if it exists
        for htElement in list_at_index:
            if htElement.key == key:
                list_at_index.remove(htElement)
                return
    
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
        

    def __iter__(self):
        return HashTableIterator(self)

    def items(self):
        return HashTableIterator(self, type="items")

    def keys(self):
        return HashTableIterator(self, type="keys")

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