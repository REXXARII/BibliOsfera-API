package cl.duoc.bibliosfera.bibliosfera_api.config;

import cl.duoc.bibliosfera.bibliosfera_api.model.*;
import cl.duoc.bibliosfera.bibliosfera_api.repository.*;
import cl.duoc.bibliosfera.bibliosfera_api.service.UsuarioService;
import cl.duoc.bibliosfera.bibliosfera_api.dto.UsuarioRegistroDTO;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    @Autowired private AutorRepository autorRepository;
    @Autowired private ObraRepository obraRepository;
    @Autowired private EdicionRepository edicionRepository;
    @Autowired private EjemplarRepository ejemplarRepository;
    @Autowired private EditorialRepository editorialRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // ESTE ES EL ÚNICO IF QUE NECESITAMOS.
        // Si ya existen roles, asumimos que todos los datos ya fueron cargados.
        if (rolRepository.count() > 0) {
            System.out.println("La base de datos ya contiene datos. No se requiere carga.");
            return; // La palabra 'return' detiene la ejecución de todo el método.
        }

        // Todo el código de creación de datos va DESPUÉS del 'if'.
        System.out.println("Iniciando carga de datos de prueba...");
        Faker faker = new Faker(Locale.forLanguageTag("es"));

        // --- 1. Crear Roles ---
        System.out.println("Creando roles...");
        Rol rolCliente = new Rol();
        rolCliente.setNombre("ROLE_CLIENTE");
        rolRepository.save(rolCliente);

        Rol rolEmpleado = new Rol();
        rolEmpleado.setNombre("ROLE_EMPLEADO");
        rolRepository.save(rolEmpleado);

        Rol rolAdmin = new Rol();
        rolAdmin.setNombre("ROLE_ADMIN");
        rolRepository.save(rolAdmin);
        System.out.println("Roles creados.");

        // --- 2. Crear Usuarios de Ejemplo ---
        System.out.println("Creando usuarios de ejemplo...");
        UsuarioRegistroDTO adminDTO = new UsuarioRegistroDTO();
        adminDTO.setNombre("Admin BibliOsfera");
        adminDTO.setEmail("admin@bibliosfera.cl");
        adminDTO.setPassword("admin123");
        adminDTO.setRoles(Set.of("ROLE_ADMIN", "ROLE_EMPLEADO"));
        usuarioService.registrarUsuario(adminDTO);

        UsuarioRegistroDTO clienteDTO = new UsuarioRegistroDTO();
        clienteDTO.setNombre("Cliente de Prueba");
        clienteDTO.setEmail("cliente@bibliosfera.cl");
        clienteDTO.setPassword("cliente123");
        clienteDTO.setRoles(Set.of("ROLE_CLIENTE"));
        usuarioService.registrarUsuario(clienteDTO);
        System.out.println("Usuarios de ejemplo creados.");

        // --- 3. Crear Catálogo ---
        System.out.println("Creando catálogo...");
        List<Editorial> editorialesGuardadas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Editorial editorial = new Editorial();
            editorial.setNombre(faker.book().publisher());
            editorialesGuardadas.add(editorialRepository.save(editorial));
        }

        List<Autor> autoresGuardados = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Autor autor = new Autor();
            autor.setNombre(faker.book().author());
            autoresGuardados.add(autorRepository.save(autor));
        }
        
        List<Categoria> categoriasGuardadas = new ArrayList<>();
        String[] nombresCategorias = {"Fantasía", "Ciencia Ficción", "Novela Histórica", "Misterio", "Biografía", "Poesía"};
        for (String nombre : nombresCategorias) {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre);
            categoriasGuardadas.add(categoriaRepository.save(categoria));
        }

        List<Obra> obrasGuardadas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Obra obra = new Obra();
            obra.setTitulo(faker.book().title());
            obra.setAnioPublicacionOriginal(faker.number().numberBetween(1800, 2024));
            obra.setAutores(Set.of(autoresGuardados.get(faker.random().nextInt(autoresGuardados.size()))));
            obra.setCategorias(Set.of(categoriasGuardadas.get(faker.random().nextInt(categoriasGuardadas.size()))));
            obrasGuardadas.add(obraRepository.save(obra));
        }

        List<Edicion> edicionesGuardadas = new ArrayList<>();
        for (Obra obra : obrasGuardadas) {
            Edicion edicion = new Edicion();
            edicion.setIsbn(faker.code().isbn13());
            edicion.setFechaPublicacion(faker.date().past(50 * 365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            edicion.setObra(obra);
            edicion.setEditorial(editorialesGuardadas.get(faker.random().nextInt(editorialesGuardadas.size())));
            edicionesGuardadas.add(edicionRepository.save(edicion));
        }

        String[] estados = {"Disponible", "Prestado", "En Reparación"};
        for (Edicion edicion : edicionesGuardadas) {
            int numeroDeEjemplares = faker.number().numberBetween(2, 6);
            for (int i = 0; i < numeroDeEjemplares; i++) {
                Ejemplar ejemplar = new Ejemplar();
                ejemplar.setCodigoBarras(faker.code().ean13());
                ejemplar.setUbicacionFisica("Estante " + faker.address().streetName().substring(0, 2).toUpperCase() + faker.number().digit());
                ejemplar.setEstado(estados[faker.random().nextInt(estados.length)]);
                ejemplar.setEdicion(edicion);
                ejemplarRepository.save(ejemplar);
            }
        }
        System.out.println("¡Carga de datos de prueba finalizada!");
    }
}
