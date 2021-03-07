import csv
import re
from datetime import datetime
from place import Place
from package import Package
from graph import Graph, Vertex
from hash_table import HashTable

packages_filename = "data/packages.csv"
places_filename = "data/places.csv"
distances_filename = "data/distances.csv"

# returns a list of packages for routing
def get_packages():
    csv = get_2d_csv(packages_filename)
    packages = []
    count = 0

    for row in csv:
        if count != 0:
            package_id = int(row[0])
            address = row[1]
            city = row[2]
            state = row[3]
            zip = row[4]
            delivery_deadline = row[5]
            mass = row[6]
            special_notes = row[7]
            arrival_time = "8:00 AM"
            required_truck = -1
            deliver_with = []

            if "Delayed on flight" in special_notes:
                arrival_time = special_notes[-7:]

            if "Can only be on truck" in special_notes:
                required_truck = int(special_notes[-1:])

            if "Must be delivered with" in special_notes:
                req_t_1 = int(special_notes[-6:-4])
                req_t_2 = int(special_notes[-2:])
                deliver_with.append(req_t_1)
                deliver_with.append(req_t_2)

            now = datetime.now()
            arrival_time_obj = datetime.strptime(arrival_time, '%I:%M %p')
            if delivery_deadline == "EOD":
                delivery_deadline_obj = "EOD"
            else: 
                delivery_deadline_obj = datetime.strptime(delivery_deadline, '%I:%M %p')
                delivery_deadline_obj = delivery_deadline_obj.replace(year=now.year, month= now.month, day=now.day)

            arrival_time_obj = arrival_time_obj.replace(year=now.year, month= now.month, day=now.day)
                
            packages.append(Package(package_id, address, city,
                                     state, zip, delivery_deadline_obj,
                                     mass, special_notes, arrival_time_obj,
                                     required_truck, deliver_with))

        count = count + 1
    
    return packages

def get_places():
    csv = get_2d_csv(places_filename)
    places = []
    
    count = 0
    for row in csv:
        if count != 0:
            name = row[0]
            address = row[1]

            if "\n" in name:
                name = name.replace("\n", "")
                
            if "\n" in address:
                address = address.replace("\n", "")

            name = name.strip()
            address = address.strip()

            regex = r'\([0-9]+\)$'
            
            address = re.sub(regex, "", address)

            places.append(Place(name, address))

        count = count + 1

    return places


# returns the graph where each vertex is an
# address and each edge is weighted by the distance
# between two vertexes. Undirected.
def get_places_graph():
    
    # create an empty graph 
    graph = Graph()

    # get a list of places
    places = get_places()

    places_hash = HashTable()

    # create a vertex for each place
    # and add the vertex to the graph.
    # also add each vertex to a HashTable
    # for fast lookup by name.
    for place in places:
        new_vertex = Vertex(place)
        graph.add_vertex(new_vertex)
        places_hash.put(place.name, new_vertex)

    distance_csv = get_2d_csv(distances_filename)
 
    # normalize the name column in distances spreadsheet
    for row in distance_csv:
        row[0] = row[0].replace("\n", "")

    # add edges to the graph based on the distances
    # spreadsheet.
    for row in distance_csv:
        start_vertex = places_hash.get(row[0])
        # print(start_vertex.value)
        # loop through each item in the row
        # the current index - 1 is the index of the
        # place corresponding to the end_vertex
        for i in range(1, len(row)):
            end_index = i - 1
            end_vertex = places_hash.get(places[end_index].name)

            weight = row[i]
            # set weight to None if it is blank or 0
            if weight == "" or float(weight) <= 0:
                weight = None
            
            # print(f'\t{end_vertex.value} has weight {weight}')
            
            # add the edge to the graph with the given weight
            if weight != None:
                graph.add_undirected_edge(start_vertex, end_vertex, float(weight))

    return graph
        

def get_2d_csv(filename):
    with open(filename) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=",")
        rows = []

        for row in csv_reader:
            rows.append(row)

        return rows
    
    return None
        
def test():
    places = get_places()
    packages = get_packages()
    graph = get_places_graph()

    count = 0
    for vertex, adjacent_edges in graph.edges.items():
        print(vertex.value)
        for adjacent_vertex in adjacent_edges:
            print(f'\t{adjacent_vertex.value} with weight {graph.get_edge_weight(vertex, adjacent_vertex)}')
        count = count + 1 

    print(count)

    for package in packages:
        if not any(vertex.value.address == package.address for vertex in graph.edges.keys()):
            print(f'No associated place for package {package}')


    
if __name__ == "__main__":
    test()
