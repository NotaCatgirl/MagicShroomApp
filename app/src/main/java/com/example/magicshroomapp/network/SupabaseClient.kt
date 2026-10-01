package com.example.magicshroomapp.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://tsljlazjesyvapfwxpir.supabase.co",
    supabaseKey = "sb_publishable_KJjB8xzootdgsCbu9lgvWw_E2Y2lz11"
) {
    install(Postgrest)
}