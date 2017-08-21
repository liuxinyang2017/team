function diag(title, url, page) {
    var diag = new top.Dialog();
    diag.Drag = true;
    diag.Title = title;
    diag.URL = url;
    diag.Width = 469;
    diag.Height = 510;
    diag.CancelEvent = function() {
        if (typeof page !== 'undefined' && $.isFunction(window.goPage)) {
            goPage(page);
            
        } 
        else {
            location.reload();    
        }
        diag.close();
    };
    diag.show();
}

function customDiag(title, url, width, height, page) {
    var diag = new top.Dialog();
    diag.Drag = true;
    diag.Title = title;
    diag.URL = url;
    diag.Width = width;
    diag.Height = height;
    diag.CancelEvent = function() {
        if (typeof page !== 'undefined' && $.isFunction(window.goPage)) {
            goPage(page);
        }
        else {
            location.reload();
        }
        diag.close();
    };
    diag.show();
}