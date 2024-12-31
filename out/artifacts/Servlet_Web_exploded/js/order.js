
const fetchItemID = () => {
    $.ajax({
        url: "http://localhost:8080/Company/orderDetails",
        method: "GET",

        success: (res) => {
            $('#itemId').empty();
            res.items.forEach((itm) => {
                let itemID = `<option>${itm.code}</option>`;
                $('#itemId').append(itemID);
            });
        },
        error: () => {
            console.log("can't load Item IDs");
            alert("Failed to load Item IDs");
        }
    });
};

const fetchCustomerId = () => {
    $.ajax({
        url: "http://localhost:8080/Company/orderDetails",
        method: "GET",

        success: (res) => {
            $('#customerId').empty();
            res.customers.forEach((cus) => {
                let dataID = `<option>${cus.id}</option>`;
                $('#customerId').append(dataID);
            });
        },

        error: () => {
            console.log("can't load Customer IDs");
            alert("Failed to load Customer IDs");
        }
    });
};


fetchItemID();
fetchCustomerId();


