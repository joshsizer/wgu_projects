import csv_reader
from router import get_package_status
from datetime import datetime

def main():
    packages = csv_reader.get_packages()    
    graph = csv_reader.get_places_graph()
    start_vertex = None

    # get a reference to the hub vertex
    for vertex in graph.edges.keys():
        if vertex.value.name == "Western Governors University":
            start_vertex = vertex
            break
    
    end_time = "EOD"
    #end_time = datetime.now().replace(hour=9, minute=50, second=0, microsecond=0)
    package_status = get_package_status(graph, start_vertex, packages, end_time)

    print(len(package_status))
    for package in package_status:
        print(package)
    

if __name__ == "__main__":
    main()    