package service;

import data.DataStore;
import model.Booking;
import model.Contract;

/**
 * ContractService generates a Contract from a priced Booking.
 */
public class ContractService {

    private final DataStore dataStore;

    public ContractService(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public Contract generateContract(Booking booking) {
        String contractId = dataStore.nextContractId();
        Contract contract = new Contract(
                contractId,
                booking.getBookingId(),
                booking.getCustomer(),
                booking.getEvent(),
                booking.getVenue(),
                booking.getDecoration(),
                booking.getPhotography(),
                booking.getCatering(),
                PricingService.CUSTOMER_DISCOUNT_RATE * 100,
                PricingService.PROVIDER_INCENTIVE_RATE * 100
        );
        booking.setContract(contract);
        return contract;
    }

    public void acceptContract(Contract contract) {
        contract.setContractStatus("ACCEPTED");
    }
}
