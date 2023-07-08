package com.hms.springbackendhms.restapi;

import com.hms.springbackendhms.config.JwtService;
//import com.hms.springbackendhms.db.VirtualDatabase;
import com.hms.springbackendhms.patient.PatientService;
import com.hms.springbackendhms.request.FindAvailableAppointmentsByDoctorRequest;
import com.hms.springbackendhms.response.FindAvailableAppointmentsByDoctorResponse;
import com.hms.springbackendhms.appointment.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.List;

@RestController
@RequestMapping("/restapi/find_available_appointments_by_doctor")
@RequiredArgsConstructor
public class FindAvailableAppointmentsByDoctor {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PatientService patientService;

    @PostMapping
    public FindAvailableAppointmentsByDoctorResponse findAvailableAppointments(
            @CookieValue(name="token", defaultValue = "") String token,
            @RequestBody FindAvailableAppointmentsByDoctorRequest request
    )
    {
        System.out.println(request.getDoctorFirstname() +
                " " + request.getDoctorLastname() +
                " " + request.getOfficeCity() +
                " " + request.getDoctorSpecialisation());

        if(token.isBlank()){
            return null;
        }

        String userEmail = jwtService.extractUsername(token);
        if(userEmail != null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(token, userDetails)) {

                if (patientService.findPatientByEmail(userEmail).isPresent()) {

                    // find a doctor that match
                    // with user input

                    Appointment appointment1 = Appointment
                            .builder()
                            .start_time("16:00")
                            .end_time("16:30")
                            .build();

                    Appointment appointment2 = Appointment
                            .builder()
                            .start_time("19:00")
                            .end_time("19:30")
                            .build();

                    List<Appointment> appointments =
                            (List.of(appointment1, appointment2));

                    return FindAvailableAppointmentsByDoctorResponse
                            .builder()
                            .availableAppointments(appointments)
                            .build();
                }
            }
        }
        return null;
    }
}
