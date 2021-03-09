from hash_table import HashTable
from queue import PriorityQueue
from datetime import datetime, timedelta
import math

# constants that can be adjusted, but
# are set by the project's contraints
max_drivers = 2
max_packages = 8
average_speed = 18

# a representation of a Truck. The import part of 
# a Truck is its ID and HashTable of packages.
class Truck():
    def __init__(self, id, initial_vertex):
        self.id = id
        self.current_vertex = initial_vertex
        self.packages = HashTable()

# An arrival event used for simulating the day
# Contains the time a truck arrives at a vertex
class ArrivalEvent():
    def __init__(self, time, truck, vertex):
        self.time = time
        self.truck = truck
        self.vertex = vertex

    # comparison operators implemented
    # in order for a PriorityQueue to 
    # be able to sort ArrivalEvents
    # based on the time component
    def __lt__(self, obj):
        return self.time.__lt__(obj.time)

    def __le__(self, obj):
        return self.time.__le__(obj.time)

    def __eq__(self, obj):
        return self.time.__eq__(obj.time)

    def __ne__(self, obj):
        if obj == None:
            return True
        return self.time.__ne__(obj.time)

    def __gt__(self, obj):
        return self.time.__gt__(obj.time)

    def __ge__(self, obj):
        return self.time.__ge__(obj.time)

# the main function of this program which provides a solution
# to the stated problem
def get_package_status(graph, start_vertex, packages, end_time):
    # create the trucks
    truck1 = Truck(1, start_vertex)
    truck2 = Truck(2, start_vertex)
    #truck3 = Truck(3, start_vertex, has_driver=False)

    graph.trucks = [truck1, truck2]

    current_vertex = start_vertex
    
    current_time = datetime.now().replace(hour=8, minute=00, second=0, microsecond=0)

    arrival_event_q = PriorityQueue()
    arrival_event_q.put(ArrivalEvent(current_time, graph.trucks[0], current_vertex))
    # ensures that truck 2 is loaded after truck 1
    current_time = current_time.replace(minute=1)
    arrival_event_q.put(ArrivalEvent(current_time, graph.trucks[1], current_vertex))
    #arrival_event_q.put(ArrivalEvent(current_time, graph.trucks[2], current_vertex))

    # how many of our deadlines are end of day?
    early_delivery_count = 0

    for package in packages:
        if package.delivery_deadline != "EOD":
            early_delivery_count = early_delivery_count + 1

    max_early_deadlines = math.ceil(early_delivery_count / max_drivers)

    # num events for debug output
    num_events = 0

    # total distance traveled so far
    total_distance = 0

    # which truck load are we on?
    truck_load = 0

    # keeps track of all the delivered packages
    delivered_packages = []
   
    # run until there are no more arrival events, or until the current time is after
    # the end time
    while not arrival_event_q.empty() and (end_time == "EOD" or current_time < end_time):
        num_events += 1
        # set some references from the current_arrival_event
        current_arrival_event = arrival_event_q.get()
        current_time = current_arrival_event.time
        current_vertex = current_arrival_event.vertex
        current_truck = current_arrival_event.truck
        my_print(f'\nEvent # {str(num_events).zfill(2)} {" "*20} |-- truck # {current_truck.id} --| @ {current_time} - {total_distance}')

        # either load the truck or deliver packages
        if current_vertex == start_vertex and len(packages) > 0:
            packages = load_truck_manually(current_arrival_event, packages, truck_load)
            truck_load += 1
        elif current_vertex != start_vertex:
            delivered_packages.extend(deliver_packages(current_truck, graph, current_vertex, current_time))

        # get the distance traveled to the next arrival event as well as the next arrival event
        added_distance, next_event = generate_next_arrival_event(current_arrival_event, graph, start_vertex, packages)
        total_distance += added_distance
    
        # add the new arrival event to the queue if it exists
        if next_event != None:
            arrival_event_q.put(next_event)
        
        # this is to ensure that the progam halts at the correct time
        if not arrival_event_q.empty():
            current_time = arrival_event_q.queue[0].time
    
    # gather up all of the packages
    all_packages = []
    all_packages.extend(delivered_packages)
    all_packages.extend(packages)
    for truck in graph.trucks:
        for package_at_address in truck.packages:
            for package in package_at_address:
                all_packages.append(package)

    # return the packages and the total distance traveled by all trucks
    return all_packages, total_distance

# loads the truck manually
def load_truck_manually(arrival_event, packages, truck_load):
    package_hash = HashTable()
    truck = arrival_event.truck
    current_time = arrival_event.time

    # turn the list of packages into a HashTable
    # where the key is the package's ID
    for package in packages:
        package_hash.put(package.package_id, package)

        # change the delivery address of package 9 at the correct time of day
        if package.package_id == 9 and arrival_event.time >= datetime.now().replace(hour=10, minute=20, second=0, microsecond=0):
            package.address = "410 S State St"
            package.zip = "84111"


    if truck_load == 0:
        # deliver with only!
        # 4580 S 2300 E
        truck.packages.put(package_hash.get(15).address, [package_hash.get(15), package_hash.get(16), package_hash.get(34)])
        # 2010 W 500 S
        truck.packages.put(package_hash.get(13).address, [package_hash.get(13), package_hash.get(39)])
        # 4300 S 1300 E
        truck.packages.put(package_hash.get(14).address, [package_hash.get(14)])
        # 177 W Price Ave
        truck.packages.put(package_hash.get(19).address, [package_hash.get(19)])
        # 3595 Main St
        truck.packages.put(package_hash.get(20).address, [package_hash.get(20), package_hash.get(21)])

        
    elif truck_load == 1:
        # early delivery
        # 410 S State St
        truck.packages.put(package_hash.get(5).address, [package_hash.get(5), package_hash.get(37), package_hash.get(38)])
         # 195 W Oakland Ave
        truck.packages.put(package_hash.get(1).address, [package_hash.get(1)])
        # 1330 2100 S
        truck.packages.put(package_hash.get(7).address, [package_hash.get(7), package_hash.get(29)])
        # 300 State St
        truck.packages.put(package_hash.get(8).address, [package_hash.get(8), package_hash.get(30)])

        # must be truck 2
        # 233 Canyon Rd
        truck.packages.put(package_hash.get(3).address, [package_hash.get(3)])
        # 1488 4800 S
        truck.packages.put(package_hash.get(18).address, [package_hash.get(18)])
        # 2300 Parkway Blvd
        truck.packages.put(package_hash.get(36).address, [package_hash.get(36)])
    
    elif truck_load == 2:
        # arrive late
        # 3060 Lester St
        truck.packages.put(package_hash.get(6).address, [package_hash.get(6)])
        # 5383 South 900 East #104
        truck.packages.put(package_hash.get(25).address, [package_hash.get(25), package_hash.get(26)])
        # 2835 Main St
        truck.packages.put(package_hash.get(28).address, [package_hash.get(28)])
        # 3365 S 900 W
        truck.packages.put(package_hash.get(31).address, [package_hash.get(31), package_hash.get(32)])
    elif truck_load == 3:
        # no condition

        # 2530 S 500 E
        truck.packages.put(package_hash.get(2).address, [package_hash.get(2), package_hash.get(33)])
        # 380 W 2880 S
        truck.packages.put(package_hash.get(4).address, [package_hash.get(4), package_hash.get(40)])
        # 600 E 900 South
        truck.packages.put(package_hash.get(10).address, [package_hash.get(10)])
        # 2600 Taylorsville Blvd
        truck.packages.put(package_hash.get(11).address, [package_hash.get(11)])
        # 3575 W Valley Central Station bus Loop
        truck.packages.put(package_hash.get(12).address, [package_hash.get(12)])
        # 3148 S 1100 W
        truck.packages.put(package_hash.get(17).address, [package_hash.get(17)])
        # 6351 South 900 East
        truck.packages.put(package_hash.get(22).address, [package_hash.get(22)])
        # 5100 South 2700 West
        truck.packages.put(package_hash.get(23).address, [package_hash.get(23)])
        # 5025 State St
        truck.packages.put(package_hash.get(24).address, [package_hash.get(24)])
        # 1060 Dalton Ave S
        truck.packages.put(package_hash.get(27).address, [package_hash.get(27), package_hash.get(35)])
    elif truck_load == 4:
        truck.packages.put(package_hash.get(9).address, [package_hash.get(9)])

    # Remove the loaded packages from the total package list
    # At the same time, set those packages delivery status and loaded_at
    # times
    for packages_at_address in truck.packages:
        for package in packages_at_address:
            package.delivery_status = "en route"
            package.loaded_at = arrival_event.time
            my_print(f'{" "*20} load_truck |-- truck # {arrival_event.truck.id} --| loading ("{package.package_id}", "{package.address}")')
            if package in packages:
                packages.remove(package)

    return packages
    

# def load_truck(arrival_event, packages, max_early_deadlines):
#     my_print(f'{" "*20} load_truck |-- truck # {arrival_event.truck.id} --| ({len(packages)}) packages left')
#     # generate a list of sets of packages that must be delivered together
#     deliver_with_sets = []
    
#     for package in packages:
#         if len(package.deliver_with) > 0:
#             lis = package.deliver_with.copy()
#             lis.append(package.package_id)
#             new_set = set(lis)
            
#             for i in range(len(deliver_with_sets)):
#                 if len(deliver_with_sets[i].intersection(new_set)) > 0:
#                     new_set = deliver_with_sets[i].union(new_set)
#                     del deliver_with_sets[i]
#                     break
            
#             deliver_with_sets.append(new_set)

#     # remove from our potential packages ones that
#     # cannot be on this truck or have not arrived at the hub yet
#     possible_packages = HashTable()
#     for package in packages:
#         if package.package_id == 9 and arrival_event.time < datetime.now().replace(hour=10, minute=20, second=0, microsecond=0):
#             continue
#         elif package.package_id == 9:
#             package.address = "410 S State St"
#             package.zip = "84111"

#         if package.arrival_time <= arrival_event.time and (package.required_truck == -1 or arrival_event.truck.id == package.required_truck):
#             possible_packages.put(package.package_id, package)

#     # if there are any packages in any of the deliver_with_sets that are not
#     # in the possible packages, every package in that set cannot go out yet
#     for i in range(len(deliver_with_sets)):
#         for package_id in deliver_with_sets[i]:
#             if not any(package_id == package.package_id for package in possible_packages):
#                 possible_packages = remove_set_of_packages(deliver_with_sets[i], possible_packages)
#                 del deliver_with_sets[i]
#                 break

#     possible_packages_sorted_by_deadline = []

#     for package in possible_packages:
#         possible_packages_sorted_by_deadline.append(package)

#     possible_packages_sorted_by_deadline.sort(key=lambda x: x.delivery_deadline_for_sort(), reverse=True)

#     i = 1
#     num_early_deadlines = 0

#     # now add all the packages that have to be delivered earliest:
#     while i <= max_packages and len(possible_packages_sorted_by_deadline) > 0:
#         to_load = possible_packages_sorted_by_deadline.pop(-1)
#         loaded_set = False

#         for deliver_with_set in deliver_with_sets:
#             if to_load.package_id in deliver_with_set and len(deliver_with_set) <= max_packages - i + 1:
#                 num_added, num_early_added = add_set_to_truck(deliver_with_set, arrival_event.truck, possible_packages, possible_packages_sorted_by_deadline)
#                 i += num_added
#                 num_early_deadlines += num_early_added
#                 loaded_set = True
#                 break
                
#         if not loaded_set:
#             if to_load.delivery_deadline == "EOD" or num_early_deadlines < max_early_deadlines:
#                 list_at_address = arrival_event.truck.packages.get(to_load.address)
#                 if list_at_address == None:
#                     list_at_address = []
                

#                 list_at_address.append(to_load)

#                 arrival_event.truck.packages.put(to_load.address, list_at_address)
#                 i += 1


#                 if to_load.delivery_deadline != "EOD":
#                     num_early_deadlines += 1

    
#     for packages_at_address in arrival_event.truck.packages:
#         for package in packages_at_address:
#             package.delivery_status = "en route"
#             my_print(f'{" "*20} load_truck |-- truck # {arrival_event.truck.id} --| loading ("{package.package_id}", "{package.address}")')
#             if package in packages:
#                 packages.remove(package)

#     return packages
        
# mark the packages on the truck at the vertex as delivered
def deliver_packages(truck, graph, vertex, time):
    # grab the packages needing to be delivered at this address
    packages_to_deliver = truck.packages.get(vertex.value.address)

    # remove those packages from the truck
    if packages_to_deliver is not None:
        truck.packages.remove(vertex.value.address)
    my_print(f'{" "*14} deliver_packages |-- truck # {truck.id} --| delivering package(s) id of ({", ".join(str(x.package_id) for x in packages_to_deliver)}) with delivery deadline ({", ".join(str(x.delivery_deadline) for x in packages_to_deliver)})')
    
    # update the package to reflect that it was delivered
    for package_to_deliver in packages_to_deliver:
        package_to_deliver.delivery_status = "delivered"
        package_to_deliver.delivery_time = time
        package_to_deliver.delivered_on = truck.id

    return packages_to_deliver

# generate the next arrival event
# that is, given a current time, location, truck (arrival_event) and number of packages left,
# create another arrival_event at the time and location that the truck will travel to next.
# the decision of where to go next is arrived though the two heuristics of urgency and
# distance from the current vertex.
def generate_next_arrival_event(arrival_event, graph, start_vertex, packages_left):

    # check if the truck has any more packages:
    needs_to_deliver = needs_to_deliver_packages(arrival_event.truck)
    
    my_print(f'{" "*1} generating_next_arrival_event |-- truck # {arrival_event.truck.id} --|', end="")
    if not needs_to_deliver and arrival_event.vertex != start_vertex:
        # return this truck to the start vertex since it's delivered all its
        # packages
        my_print(' packages empty. must return to home base')
        distance = graph.weights.get((arrival_event.vertex, start_vertex))
        new_arrival_time = calculate_next_time(arrival_event.time, distance)

        return distance, ArrivalEvent(new_arrival_time, arrival_event.truck, start_vertex)
    elif not needs_to_deliver:
        # if there are packages left but they're not available yet, wait
        if len(packages_left) > 0:
            my_print(' must wait for packages.')
            return 0, ArrivalEvent(arrival_event.time + timedelta(minutes=5), arrival_event.truck, start_vertex)
        
        # otherwise the truck needs to retire for the day
        my_print(' packages empty. truck retiring')
        return 0, None

    # urgent set contains those packages that have an early delivery deadline
    urgent_set = []
    # non-urgent set are packages that do not have an early delivery deadline
    non_urgent_set = []
    for neighbor in graph.edges.get(arrival_event.vertex):
        neighbor_package = arrival_event.truck.packages.get(neighbor.value.address)
        if neighbor_package is not None:
            # iterate through them
            for package in neighbor_package:
                # if they are still needing to be delivered
                if package.delivery_status == "en route" and package.delivery_deadline != "EOD":
                    urgent_set.append((neighbor, package))
                elif package.delivery_status == "en route" and package.delivery_deadline == "EOD":
                    non_urgent_set.append((neighbor, package))

    # sort the sets so we can pull the earliest deadline
    urgent_set.sort(key=lambda x: x[1].delivery_deadline_for_sort())
    non_urgent_set.sort(key=lambda x: x[1].delivery_deadline_for_sort())

    # if there is no urgent set, perform the following operations on the non_urgent_set
    if len(urgent_set) <= 0:
        urgent_set = non_urgent_set
        
    # simply find the vertex that is closest and travel to it
    top_of_urgent_set = urgent_set[0]
    minimum_vertex = top_of_urgent_set[0]
    minimum_distance = graph.weights.get((arrival_event.vertex, minimum_vertex))

    for i in range(1, len(urgent_set)):
        if urgent_set[i][1].delivery_deadline == top_of_urgent_set[1].delivery_deadline:
            distance = graph.weights.get((arrival_event.vertex, urgent_set[i][0]))
            if distance < minimum_distance:
                minimum_distance = distance
                minimum_vertex = urgent_set[i][0]

    new_arrival_time = calculate_next_time(arrival_event.time, minimum_distance)
    return minimum_distance, ArrivalEvent(new_arrival_time, arrival_event.truck, minimum_vertex)

# returns true if any package on the truck
# has status "en route"
def needs_to_deliver_packages(truck):
    for packages_at_address in truck.packages:
        for package in packages_at_address:
            if package.delivery_status == "en route":
                return True

# removes a set of packages
# from the list
def remove_set_of_packages(set_, packages):
    for item in set_:
        packages.remove(item)

    return packages

# adds the packages with the id contained in set 
# to the truck.
# returns the number of packages added as well as
# the number of packages added that contained early deadlines
def add_set_to_truck(set_, truck, packages, packages_sorted):
    num_early_deadlines = 0
    for package_id in set_:
        to_load = packages.get(package_id)
        list_at_address = truck.packages.get(to_load.address)
        if list_at_address == None:
            list_at_address = []

        list_at_address.append(to_load)
        truck.packages.put(to_load.address, list_at_address)
        
        if to_load.delivery_deadline != "EOD":
            num_early_deadlines = num_early_deadlines + 1

        if to_load in packages_sorted:
            packages_sorted.remove(to_load)

    return len(set_), num_early_deadlines

# calculates the time that the truck will
# arrive, given the current time and distance the
# truck is traveling
def calculate_next_time(start_time, distance):
    # distance in miles
    # average speed in miles/hour
    time_in_hours = distance / average_speed
    time_in_minutes = time_in_hours * 60

    return start_time + timedelta(minutes=time_in_minutes)


def test():
    current_time = datetime.now().replace(hour=8, minute=00, second=0, microsecond=0)

    arrival_event_q = PriorityQueue()
    # arrival_event_q.put((current_time, ArrivalEvent(current_time, None, None)))
    # current_time = current_time + timedelta(minutes=30)
    # arrival_event_q.put((current_time, ArrivalEvent(current_time, None, None)))
    # current_time = current_time - timedelta(minutes=15)
    # arrival_event_q.put((current_time, ArrivalEvent(current_time, None, None)))

    arrival_event_q.put(ArrivalEvent(current_time, None, None))
    current_time = current_time + timedelta(minutes=30)
    arrival_event_q.put(ArrivalEvent(current_time, None, None))
    current_time = current_time - timedelta(minutes=15)
    arrival_event_q.put(ArrivalEvent(current_time, None, None))

    while not arrival_event_q.empty():
        next_item = arrival_event_q.get()
        my_print(next_item.time)


def my_print(to_print, end="\n"):
    debug = False
    if debug:
        print(to_print, end=end)


if __name__ == "__main__":
    test()

    



