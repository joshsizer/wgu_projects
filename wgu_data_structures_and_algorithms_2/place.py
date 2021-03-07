class Place():
    def __init__(self, name, address):
        self.name = name
        self.address = address

    def __str__(self):
        return f'("{self.name}", "{self.address}")'

    def __repr__(self):
        return self.__str__()

    def __hash__(self):
        return hash(self.name + self.address)

    def __eq__(self, other):
        if type(self) == type(other):
            return self.name == other.name and self.address == other.address
        return False