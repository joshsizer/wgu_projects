from hash_table import HashTable
from place import Place

class Vertex():
    def __init__(self, value):
        self.value = value

    def __hash__(self):
        return hash(self.value)
  
    def __eq__(self, other):
        if type(self) == type(other):
            return self.value == other.value
        return False

class Graph():
    def __init__(self):
        self.edges = HashTable()
        self.weights = HashTable()

    def add_vertex(self, new_vertex):
        self.edges.put(new_vertex, [])

    def add_directed_edge(self, from_vertex, to_vertex, weight=1):
        self.edges.get(from_vertex).append(to_vertex)
        self.weights.put((from_vertex, to_vertex), weight)

    def add_undirected_edge(self, vertex1, vertex2, weight=1):
        self.add_directed_edge(vertex1, vertex2, weight)
        self.add_directed_edge(vertex2, vertex1, weight)
    
    def get_edge_weight(self, vertex1, vertex2):
        return self.weights.get((vertex1, vertex2))

def test():
    v1 = Vertex(Place("Western Govenors University", "4001 South 700 East Salt Lake City, UT 84107"))
    v2 = Vertex(Place("International Peace Gardens", "1060 Dalton Ave S (84104)"))

    graph = Graph()
    graph.add_vertex(v1)
    graph.add_vertex(v2)
    graph.add_undirected_edge(v1, v2, 7.2)

if __name__ == "__main__":
    test()