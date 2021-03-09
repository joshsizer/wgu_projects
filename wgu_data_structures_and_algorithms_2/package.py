import datetime

# An object for representing a package to be delivered.
class Package():
    def __init__(self, package_id, address, city, state, zip, delivery_deadline, mass, special_notes,
                arrival_time="8:00 AM", required_truck=-1, deliver_with=[]):
        # an integer which is unique to each package
        self.package_id = package_id

        # the address this package needs to be delivered to
        self.address = address

        # the city this package needs to be delivered to
        self.city = city

        # the sate this package needs to be delivered to
        self.state = state

        # the zip code this package needs to be delivered to
        self.zip = zip

        # the time by which this package must be delivered
        self.delivery_deadline = delivery_deadline

        # the weight of the package, in kilograms
        self.mass = mass

        # any special notes that may modify what needs to happen
        # for this package
        self.special_notes = special_notes

        # the time that this package arrives to the hub
        self.arrival_time = arrival_time

        # the truck that this package is required to travel on
        self.required_truck = required_truck

        # other packages that must be delivered with this one
        self.deliver_with = deliver_with

        # if the package is "at the hub", "en route", or "delivered"
        self.delivery_status = "at the hub"

        # at what time the package is delivered
        self.delivery_time = None

        # the truck number the package was delivered on
        self.delivered_on = -1

        # the time the package was loaded onto a truck
        self.loaded_at = None

    # allows for packages to be sorted on the delivery deadline.
    def delivery_deadline_for_sort(self):
        if type(self.delivery_deadline) == type(""):
            return datetime.datetime.now().replace(hour=23, minute=59, second=59, microsecond=99999)
        else:
            return self.delivery_deadline

    # return a string representation of a package
    def __str__(self):
        return (
            f'(package_id: "{str(self.package_id).zfill(2)}" | address: "{self.address}"'
            f' | delivery_deadline: "{self.delivery_deadline}" | city: "{self.city}" | zipcode: "{self.zip}" | mass: "{self.mass}"' 
            f' | loaded_at: "{self.loaded_at}" | delivery_status: "{self.delivery_status}" | delivery_time: "{self.delivery_time}" | delivered_on truck: "{self.delivered_on}")'
        )

    # return a string representation of a package
    def __repr__(self):
        return self.__str__()

    # two packages are equal if their package ids are equal
    def __eq__(self, other):
        if type(self) == type(other):
            return self.package_id == other.package_id
        else:
            return False
