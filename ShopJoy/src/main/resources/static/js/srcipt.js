
    const bar = document.getElementById('bar');
    const close = document.getElementById('close');
    const nav = document.getElementById('navbar');
   

    if (bar) {
      bar.addEventListener('click', function() {
        nav.classList.add('active');
        console.log("Hai");
      })
    }

    if (close) {
        close.addEventListener('click', function() {
          nav.classList.remove('active');
          console.log("removeHai");
          
        })
      }


      const bar1 = document.getElementById('cart-shopping');
      const close1 = document.getElementById('close1');
      const shopping = document.getElementById('shopping');
     
  
      if (bar1) {
        bar1.addEventListener('click', function() {
          shopping.classList.add('active');
          console.log("Hai");
        })
      }
  
      if (close1) {
          close1.addEventListener('click', function() {
            shopping.classList.remove('active');
            console.log("removeHai");
            
          })
        }
        

// Example starter JavaScript for disabling form submissions if there are invalid fields
(() => {
	'use strict';

	// Fetch all the forms we want to apply custom Bootstrap validation styles to
	const forms = document.querySelectorAll('.needs-validation');

	// Loop over them and prevent submission
	Array.prototype.slice.call(forms).forEach((form) => {
		form.addEventListener('submit', (event) => {
			if (!form.checkValidity()) {
				event.preventDefault();
				event.stopPropagation();
			}
			form.classList.add('was-validated');
		}, false);
	});
})();


  