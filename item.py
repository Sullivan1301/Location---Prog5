class Item:
    def __init__(self, name: str):
        self.name = name
        self.reservations = []

    def __str__(self) -> str:
        return f"Item(name={self.name})"

    def __repr__(self) -> str:
        return self.__str__() 