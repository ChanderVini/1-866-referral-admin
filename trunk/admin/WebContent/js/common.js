var nbrOfRows = 0;

function handleSubmit (actionname) {
    document.forms[0].action = actionname;
}

function toggleChildRow(arr,rowID,childs){
    for (i=0;i<childs;i++){	
        child = document.getElementById(rowID+i);
        if (child.style.display == 'none'){
            child.style.display = 'block';
            arr.src = 'images/arrow_collapse.gif';
        }else{
            child.style.display = 'none';
            arr.src = 'images/arrow_expand.gif';
        }
    }	
}

function handlePaymentOption () {
    var ele = document.getElementById ('paymentoption');
    if (ele && ele != 'undefined') {
        var index = ele.selectedIndex;
        var val = ele.options[index].value;
        var amtele = document.getElementById ('amtcharged');
        amtele.style.display = 'none';
        var paydtele = document.getElementById ('paymentdate');
        paydtele.style.display = 'none';
        var paychecknbrele = document.getElementById ('payment.check.nbr');
        paychecknbrele.style.display = 'none';            
        var paycheckbanknameele = document.getElementById ('payment.check.bankname');
        paycheckbanknameele.style.display = 'none';
        var cctypeele = document.getElementById ('payoption.cc.type');
        cctypeele.style.display = 'none';
        var ccnbrele = document.getElementById ('payoption.cc.nbr');
        ccnbrele.style.display = 'none';
        var seccodeele = document.getElementById ('payoption.cc.seccode');
        seccodeele.style.display = 'none';
        var expmonele = document.getElementById ('payoption.cc.expmon');
        expmonele.style.display = 'none';
        var expyearele = document.getElementById ('payoption.cc.expyear');
        expyearele.style.display = 'none';
        if (val != 'DEF' && val != 'DNC') {
            var amtele = document.getElementById ('amtcharged');
            amtele.style.display = 'inline';
        }

        if (val == 'CASH' || val == 'CHECK') {
            var paydtele = document.getElementById ('paymentdate');
            paydtele.style.display = 'inline';
        }
        
        if (val == 'CHECK') {
            var paychecknbrele = document.getElementById ('payment.check.nbr');
            paychecknbrele.style.display = 'inline';            
            var paycheckbanknameele = document.getElementById ('payment.check.bankname');
            paycheckbanknameele.style.display = 'inline';
        }
        
        if (val == 'CC') {
            var cctypeele = document.getElementById ('payoption.cc.type');
            cctypeele.style.display = 'inline';
            var ccnbrele = document.getElementById ('payoption.cc.nbr');
            ccnbrele.style.display = 'inline';
            var seccodeele = document.getElementById ('payoption.cc.seccode');
            seccodeele.style.display = 'inline';
            var expmonele = document.getElementById ('payoption.cc.expmon');
            expmonele.style.display = 'inline';
            var expyearele = document.getElementById ('payoption.cc.expyear');
            expyearele.style.display = 'inline';
        }
    }
}