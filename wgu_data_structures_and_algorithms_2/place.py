# A representation of a location for packages
# to be delivered to.
class Place():
    def __init__(self, name, address):
        # the name of this place
        self.name = name
        # the address of this place
        self.address = address

    # return a string representation of this Place
    def __str__(self):
        return f'("{self.name}", "{self.address}")'

    # return a string representation of this Place 
    def __repr__(self):
        return self.__str__()

    # the hash of this object is defined as the hash of
    # its name and address added together.
    def __hash__(self):
        return hash(self.name + self.address)

    # A Place is equal to another place if both place's 
    # Names and Addresses are equal
    def __eq__(self, other):
        if type(self) == type(other):
            return self.name == other.name and self.address == other.address
        return False