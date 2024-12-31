
const fetchData = ()=>{

    $.ajax({
        url:'http://localhost:8080/Company/cus',
        method:"GET",

        success:(res)=>{
            $('#tbody').empty();

            res.forEach((cus)=>{

                let data =
                    `<tr>
                         <td>${cus.id}</td>
                         <td>${cus.name}</td>
                         <td>${cus.address}</td>
                         
                     
                    <td>
                     <button class="btn btn-success" id="btnUpdate"class="btn btn-primary mb-3 text-right" data-bs-toggle="modal"
                      data-bs-target="#updateModal" onclick="edit('${cus.id}','${cus.name}','${cus.address}')">Edit</button>
                     <button class="btn btn-danger" id="btnDelete" onclick="deleteCus('${cus.id}')">Delete</button>
                   </td>
                     </tr>`

                $('#tbody').append(data);
            })
        },

        error:(err)=>{
            console.log("error")
        }

    })

}
const clearFields =()=>{

    $('#name').val("");
    $('#address').val("");
}



fetchData()

$("#btnSave").click(()=>{

    let id = $('#id').val();
    let name = $('#name').val();
    let address = $('#address').val();

    $.ajax({
        url: `http://localhost:8080/Company/cus?id=${id}&name=${name}&address=${address}`,
        method: "POST",

        success:(res)=>{
            fetchData()
            clearFields()

            Swal.fire({
                title: "Added!",
                icon: "success",
                draggable: true
            });
        },
        error:(err)=>{
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Can't Add Customer",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
        }
    })



})

$('#btnClear').click(()=>{
    clearFields()
})

$('#btnUpdate').click(()=>{
    let id = $('#idU').val();
    let name = $('#nameU').val();
    let address = $('#addressU').val();


    $.ajax({
        url: `http://localhost:8080/Company/cus?id=${id}&name=${name}&address=${address}`,
        method:"PUT",
        success:()=>{
            fetchData();
            Swal.fire({
                title: "Updated!",
                icon: "success",
                draggable: true
            });
        },

        error:()=>{
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Can't Update Customer",
                footer: '<a href="#">Why do I have this issue?</a>'
            });
        }
    })
})

const  edit = (id,name,address)=>{
   $('#idU').val(id);
     $('#nameU').val(name);
    $('#addressU').val(address);
}

const deleteCus = (id)=> {

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
                url: `http://localhost:8080/Company/cus?id=${id}`,
                method: 'DELETE',

                success: () => {
                    fetchData()
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




