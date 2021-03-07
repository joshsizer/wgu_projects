import datetime

class Package():
    def __init__(self, package_id, address, city, state, zip, delivery_deadline, mass, special_notes,
                arrival_time="8:00 AM", required_truck=-1, deliver_with=[]):
        self.package_id = package_id
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.delivery_deadline = delivery_deadline
        self.mass = mass
        self.special_notes = special_notes
        self.arrival_time = arrival_time
        self.required_truck = required_truck
        self.deliver_with = deliver_with
        self.delivery_status = "at the hub"
        self.delivery_time = None

    def delivery_deadline_for_sort(self):
        if type(self.delivery_deadline) == type(""):
            return datetime.datetime.now().replace(hour=23, minute=59, second=59, microsecond=99999)
        else:
            return self.delivery_deadline

    def __str__(self):
        return (
            f'(package_id: "{str(self.package_id).zfill(2)}" | address: "{self.address}"'
            f' | delivery_deadline: "{self.delivery_deadline}" | city: "{self.city}" | zipcode: "{self.zip}" | mass: "{self.mass}"' 
            f' | delivery_status: "{self.delivery_status}" | delivery_time: "{self.delivery_time}")'
        )

    def __repr__(self):
        return self.__str__()

    def __eq__(self, other):
        if type(self) == type(other):
            return self.package_id == other.package_id
        else:
            return False
