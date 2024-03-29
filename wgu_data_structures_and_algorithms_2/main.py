# Author: Joshua Sizer
# StudentID: 001216849
import csv_reader
from router import get_package_status
from datetime import datetime

def main():
    # load in the packages and the graph
    packages = csv_reader.get_packages()    
    graph = csv_reader.get_places_graph()
    start_vertex = None

    # get a reference to the hub vertex
    for vertex in graph.edges.keys():
        if vertex.value.name == "Western Governors University":
            start_vertex = vertex
            break
    
    # ask the user for which packages to view
    which_package = input(f'What package would you like to view the status of? Enter "all" or a number between 1 and 40: ')

    # ask the user until they enter a valid answer
    ask_for_package = True
    while ask_for_package:
        
        try:
            which_package = int(which_package)
            if which_package < 1 or which_package > 40:
                raise ValueError
            ask_for_package = False
        except ValueError:
            if str(which_package).lower() != "all":
                which_package = input(f'Please enter "all" or a number between 1 and 40: ')
            else:
                ask_for_package = False

    # ask the user for what time to view the package status at
    what_time = input(f'What time would you like to view the status at? Enter in 24-hour time (HH:MM) or EOD for the end of day: ').upper()

    # ask the user until they enter a valid answer
    ask_for_time = True
    while ask_for_time:
        try:
            what_time = datetime.strptime(what_time, '%H:%M')
            now = datetime.now()
            what_time = what_time.replace(year=now.year, month= now.month, day=now.day)
            ask_for_time = False
        except ValueError:
            if str(what_time).lower() != "EOD".lower():
                what_time = input(f'Please enter a 24-hour time (HH:MM) or EOD: ').upper()
            else:
                ask_for_time = False

    # run the main algorithm to get a list of packages and the total distance traveled by all trucks
    package_status, total_distance = get_package_status(graph, start_vertex, packages, what_time)

    # sort the result so that when all the packages are printed, they are presented in numerical order
    # by package id
    package_status.sort(key=lambda x: x.package_id)

    # print either all of the packages or the one selected by the user
    print(f'')
    for package in package_status:
        if str(which_package).lower() == "all" or package.package_id == which_package:
            print(package)

    print(f'\nTotal distance traveled by all trucks: {total_distance}')
    

if __name__ == "__main__":
    main()    