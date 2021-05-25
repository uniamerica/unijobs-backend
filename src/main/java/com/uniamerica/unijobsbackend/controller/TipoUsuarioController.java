package com.uniamerica.unijobsbackend.controller;

import com.uniamerica.unijobsbackend.model.TipoUsuario;
import com.uniamerica.unijobsbackend.model.Usuario;
import com.uniamerica.unijobsbackend.repository.TipoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipo_usuarios")
public class TipoUsuarioController {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> BuscarTodos(){
        return ResponseEntity.ok(tipoUsuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> buscarTipoUsuario(@PathVariable Integer id){
        Optional<TipoUsuario> user = tipoUsuarioRepository.findById(id);
        if (user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @RequestMapping(value="/cadastrar_tipoUsuario", method=RequestMethod.POST)
    public String form(@Validated TipoUsuario tipoUsuario, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Verifique os campos!");
            return "redirect:/cadastrar_tipoUsuario";
        }

        tipoUsuarioRepository.save(tipoUsuario);
        attributes.addFlashAttribute("mensagem", "TipoUsuario cadastrado com sucesso!");
        return "redirect:/cadastrar_tipoUsuario";
    }
//    @PostMapping("/{id}")
//    public ResponseEntity<TipoUsuario> criarTipoUsuario(@RequestBody TipoUsuario t_user){
//        TipoUsuario saved = tipoUsuarioRepository.save(t_user);
//        return new ResponseEntity<TipoUsuario>(saved, HttpStatus.CREATED);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TipoUsuario> removerTipoUsuario(@PathVariable Integer id){
        Optional<TipoUsuario> t_user = tipoUsuarioRepository.findById(id);
        if (t_user.isEmpty()){
            return ResponseEntity.notFound().build();
        }
      tipoUsuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuario> atualizarTipoUsuario(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario){
        Optional<TipoUsuario> t_user = tipoUsuarioRepository.findById(id);
        if (t_user.isEmpty()) {
            return  ResponseEntity.notFound().build();
        }
        TipoUsuario saved = t_user.get();
        saved.setNome(tipoUsuario.getNome());
        saved = tipoUsuarioRepository.save(saved);
        return ResponseEntity.ok(saved);
    }
}
