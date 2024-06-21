const formulario = document.querySelector("form");
const Inome = document.querySelector(".nome");
const Iemail = document.querySelector(".email");
const Isenha = document.querySelector(".senha");
const Itel = document.querySelector(".tel");
const Icpf = document.querySelector(".cpf");

function cadastrar(){

    fetch("http://localhost/cadastrar"
        {
            headers:{
                'Accept': 'aplication/json',
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                nome: Inome.value,
                email: Iemail.value,
                senha: Isenha.value,
                telefone: Itel.value,
                cpf: Icpf.value
        
            })
        })
        .then(function(res){console.log(res) })
        caches(function (res){console.log(res) })

};

function limpar () {
    Inome.value = "";
    Iemail.value = "";
    Isenha.value = "";
    Itel.value = "";
    Icpf.value = "";
}

formulario,addEventListener('submit', function (event){
    event.preventDefault();
    
    cadastrar ();
    limpar ();
});