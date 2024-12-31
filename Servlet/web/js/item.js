const fetchItmData = ()=>{

    $.ajax({
        url:"http://localhost:8080/Company/item",
        method:"GET",

        success:(res)=>{
            $("#tbodyI").empty()

            console.log(res)

            res.forEach((itm)=>{

                let data =
                    `<tr>
                       <td>${itm.code}</td>
                       <td>${itm.description}</td>
                       <td>${itm.qtyOnHand}</td>
                       <td>${itm.unitPrice}</td>
                       
                        <td>
                     <button class="btn btn-success" id="btnUpdate"class="btn btn-primary mb-3 text-right" data-bs-toggle="modal"
                      data-bs-target="#ItemupdateModal" onclick="valueInTxt('${itm.code}','${itm.description}','${itm.qtyOnHand}','${itm.unitPrice}')">Edit</button>
                     <button class="btn btn-danger" id="btnDelete" onclick="deleteI('${itm.code}')">Delete</button>
                   </td>
                     </tr>`

                $("#tbodyI").append(data)
            })
        },

        error:()=>{
            console.log("error")
        }
    })
}

const valueInTxt=(code,desc,qty,price)=>{
    $("#idIU").val(code);
    $("#nameIU").val(desc);
    $("#qtyIU").val(qty);
    $("#priceIU").val(price);
}

const clearTXT=()=>{
    $("#idIU").val("code");
    $("#nameIU").val("desc");
    $("#qtyIU").val("qty");
    $("#priceIU").val("price");
}

const deleteI =(code)=>{
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `http://localhost:8080/Company/item?code=${code}`,
                method: 'DELETE',

                success: () => {
                    fetchItmData()
                },
                error: () => {
                    console.log("cant delete")
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Something went wrong!",
                        footer: '<a href="#">Why do I have this issue?</a>'
                    });
                }
            })
            Swal.fire({
                title: "Deleted!",
                text: "Your file has been deleted.",
                icon: "success"
            });
        }
    });

}

fetchItmData()

$('#btnSaveI').click(()=>{

    let code = $("#code").val();
    let desc = $('#Iname').val();
    let qty = $('#qty').val();
    let price = $('#price').val();

    $.ajax({
        url: `http://localhost:8080/Company/item?code=${code}&description=${desc}&qtyOnHand=${qty}&unitPrice=${price}`,
        method: "POST",

        success:()=>{
            fetchItmData()



            Swal.fire({
                title: "Added!",
                icon: "success",
                draggable: true
            });
        },

        error:()=>{
            console.log("cant add!!")
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Can't Add Item",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
        }
    })

})

$("#btnUpdateI").click(()=>{

    let code = $("#idIU").val();
    let desc = $("#nameIU").val();
    let qty = $("#qtyIU").val();
    let price = $("#priceIU").val();


    $.ajax({
        url:`http://localhost:8080/Company/item?code=${code}&description=${desc}&qtyOnHand=${qty}&unitPrice=${price}`,
        method:"PUT",

        success:()=>{
          fetchItmData();
          clearTXT()
            Swal.fire({
                title: "Updated!",
                icon: "success",
                draggable: true
            });
        },

        error:()=>{
            console.log("cant update")
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Can't Update Item",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
        }
    })
})

