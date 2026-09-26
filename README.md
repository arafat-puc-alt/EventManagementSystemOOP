# Event Management System — Java Swing (CSE OOP Lab Project)

## 1. Project Overview
A fully offline Java Swing desktop application where a customer logs in, chooses
an event category and type, enters event details, selects a **mandatory** venue,
optionally adds decoration/photography/catering, reviews a dynamically-priced
summary, views a contract, and confirms the booking. All data lives in memory
(`ArrayList`/`HashMap`) — no database, no network, no external framework.

Run it with:
```
javac -d bin -encoding UTF-8 $(find src -name "*.java")
java -cp bin Main
```
Login with `admin` / `admin123`.

## 2. Class Diagram Explanation
```
                 Event (abstract)
                 /            \
        SocialEvent          CorporateEvent
        /    |    \             /       \
  Birthday Wedding Anniversary Conference AlumniEvent
```
`Event` declares shared fields (id, name, customer, date, guests, base cost) and
two abstract methods, `calculateCost()` and `getEventDescription()`. `SocialEvent`
and `CorporateEvent` add a category-level service fee and implement those methods;
each leaf class calls `super.calculateCost()` and adds its own surcharge (e.g.
`Wedding` adds a bridal-suite fee, `Conference` adds an AV-equipment fee) — a
genuine three-level hierarchy where every level has real behavior, not just
inheritance for decoration.

## 3. Interface Explanation
`interfaces.Trackable` declares `isAvailable()` and `getContactInfo()`. It is
implemented by `Venue`, `Decoration`, `PhotographyPackage`, and
`CateringPackage` — four different provider types handled polymorphically
through the same interface reference where needed.

## 4. Encapsulation
Every model class (`Event` and its subclasses, `Customer`, `Venue`,
`Decoration`, `PhotographyPackage`, `CateringPackage`, `Booking`, `Contract`)
keeps all fields `private` and exposes them only through getters/setters
(with validation, e.g. `Event.setGuestCount` rejects values `<= 0`).

## 5. Polymorphism
- **Overriding:** `calculateCost()` and `getEventDescription()` behave
  differently in every subclass.
- **Overloading:** `Event.calculateCost()` vs. `Event.calculateCost(int
  newGuestCount)`.
- **Dynamic binding:** any `Event event = new Birthday(...)` /
  `new Wedding(...)` / `new Conference(...)` call to `event.calculateCost()`
  runs the correct overridden method at runtime — exercised directly in
  `EventDetailsPanel.buildEvent(...)`, which returns different concrete types
  through a single `Event` reference depending on what the user picked.

## 6. Exception Handling
- `exceptions.CapacityExceededException` (custom **checked** exception) is
  thrown by `BookingService.validateVenueCapacity(...)` and caught in
  `VenuePanel`/`BookingSummaryPanel`, showing a friendly dialog instead of
  crashing.
- `NumberFormatException` is caught in `EventDetailsPanel` when the guest
  count field contains non-numeric input.

## 7. Collections
`data.DataStore` holds `ArrayList<Venue>`, `ArrayList<Decoration>`,
`ArrayList<PhotographyPackage>`, `ArrayList<CateringPackage>`,
`ArrayList<Booking>`, plus `HashMap<String, Venue>` and
`HashMap<String, Booking>` for O(1) ID lookup. It demonstrates add, remove,
search-by-ID, and iteration (used when rendering cards and the bookings table).

## 8. GUI
Built with `JFrame` + `CardLayout` (`MainFrame`) switching between 13
`JPanel` screens (`LoginPanel`, `DashboardPanel`, ... `MyBookingsPanel`).
Uses `JTextField`, `JPasswordField`, `JButton`, `JLabel`, `JTable`,
`JOptionPane`, and `ActionListener` throughout. Business logic lives in
`service/` classes, not inside listeners — panels call
`bookingService.validateVenueCapacity(...)`,
`pricingService.calculateBookingTotal(...)`, etc., and only handle
navigation/UI concerns themselves.

## 9. Booking Flow
```
Login -> Dashboard -> Event Category -> Event Type -> Event Details
      -> Venue (required) -> Decoration (optional) -> Photography (optional)
      -> Catering (optional) -> Booking Summary -> Contract -> Confirmation
```
Venue selection has no Skip option and is capacity-validated before the wizard
can proceed. All three add-on services can be skipped independently and the
booking still completes correctly (skipped services are stored as `null` and
priced as zero — see `PricingService.calculateBookingTotal`).

## 10. OOP Requirement Mapping

| Requirement | Class / Method |
|---|---|
| Encapsulation | All `model/*` classes (private fields + getters/setters) |
| Abstraction | `model.Event` (abstract class + abstract methods) |
| Inheritance (3 levels) | `Event -> SocialEvent -> Birthday/Wedding/Anniversary`, `Event -> CorporateEvent -> Conference/AlumniEvent` |
| Polymorphism (overriding) | `calculateCost()` / `getEventDescription()` in every subclass |
| Polymorphism (overloading) | `Event.calculateCost()` / `Event.calculateCost(int)` |
| Dynamic binding | `EventDetailsPanel.buildEvent(...)` returning an `Event` reference |
| Interface | `interfaces.Trackable`, implemented by 4 provider classes |
| Composition | `model.Booking` (holds Customer, Event, Venue, Decoration, Photography, Catering, Contract) |
| Custom checked exception | `exceptions.CapacityExceededException` |
| Built-in exception handling | `NumberFormatException` in `EventDetailsPanel` |
| ArrayList / HashMap | `data.DataStore` |
| GUI | `gui/MainFrame` + 13 `JPanel` screens, `CardLayout` |
| Event-driven programming | `ActionListener`s on every button across the `gui` package |

## Test Scenarios (from the spec, all verified against the code paths)
1. **Full booking:** Birthday, 100 guests, Royal Palace, Luxury Decoration,
   Photography skipped, Premium Catering → Subtotal ৳195,000 → 10% discount
   ৳19,500 → Final ৳175,500.
2. **All optional services skipped:** still produces a valid, confirmable
   booking (`decoration`/`photography`/`catering` are `null`, priced as 0).
3. **Capacity exceeded:** 600 guests against a 500-capacity venue → SELECT is
   disabled and, if reached programmatically, `CapacityExceededException` is
   thrown and caught with a friendly message; user stays on Venue screen.
4. **Invalid guest input:** non-numeric guest count → caught
   `NumberFormatException`, friendly validation message shown, no crash.
