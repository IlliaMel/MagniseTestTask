package com.infinity.apps.magnisetesttask.data.remote.interceptor

import com.infinity.apps.magnisetesttask.domain.local.repository.ITokenCacheRepository
import okhttp3.Interceptor
import retrofit2.Invocation
import okhttp3.Response
import javax.inject.Inject

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated

class AuthInterceptor @Inject constructor(private val tokenCacheRepository: ITokenCacheRepository) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java) ?: return chain.proceed(request)
        val shouldAttachAuthHeader = invocation.method().annotations.any { it is Authenticated }

        return if (shouldAttachAuthHeader) {
            val token = /*tokenCacheRepository.getAccessToken()*/ "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJTUDJFWmlsdm8zS2g3aGEtSFRVU0I3bmZ6dERRN21tb3M3TXZndlI5UnZjIn0.eyJleHAiOjE3MzAwNjQ5NDksImlhdCI6MTczMDA2MzE0OSwianRpIjoiODUxNzUwMjMtODgyMS00MDhmLTgwYjYtZGQxNzU4NmFmM2Y4IiwiaXNzIjoiaHR0cHM6Ly9wbGF0Zm9ybS5maW50YWNoYXJ0cy5jb20vaWRlbnRpdHkvcmVhbG1zL2ZpbnRhdGVjaCIsImF1ZCI6WyJuZXdzLWNvbnNvbGlkYXRvciIsImJhcnMtY29uc29saWRhdG9yIiwidHJhZGluZy1jb25zb2xpZGF0b3IiLCJlZHVjYXRpb24iLCJjb3B5LXRyYWRlci1jb25zb2xpZGF0b3IiLCJwYXltZW50cyIsIndlYi1zb2NrZXRzLXN0cmVhbWluZyIsInVzZXItZGF0YS1zdG9yZSIsImFsZXJ0cy1jb25zb2xpZGF0b3IiLCJ1c2VyLXByb2ZpbGUiLCJlbWFpbC1ub3RpZmljYXRpb25zIiwiaW5zdHJ1bWVudHMtY29uc29saWRhdG9yIiwiYWNjb3VudCJdLCJzdWIiOiI5NWU2NmRiYi00N2E3LTQ4ZDktOWRmZS00ZWM2Y2U0MWNiNDEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhcHAtY2xpIiwic2Vzc2lvbl9zdGF0ZSI6ImZiNjc2NjU0LWFjZDUtNDY1Yi04YmNkLWI3Mjg3Y2JjNTIxNCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLWZpbnRhdGVjaCIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlcnMiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiJmYjY3NjY1NC1hY2Q1LTQ2NWItOGJjZC1iNzI4N2NiYzUyMTQiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLWZpbnRhdGVjaCIsInVtYV9hdXRob3JpemF0aW9uIiwidXNlcnMiXSwibmFtZSI6IkRlbW8gVXNlciIsInByZWZlcnJlZF91c2VybmFtZSI6InJfdGVzdEBmaW50YXRlY2guY29tIiwiZ2l2ZW5fbmFtZSI6IkRlbW8iLCJmYW1pbHlfbmFtZSI6IlVzZXIiLCJlbWFpbCI6InJfdGVzdEBmaW50YXRlY2guY29tIn0.GF3CvRhzAdMPYMFLi0b0tB6r7xAGNFijSIYHyfWfg6XIhJnlxvre1bxK5uzjZBnwSX-bJVdvqVYrPACo9lWiIpi4KksO9LMBrDb7yqasZGcFc2BGkdvSJcxVFsNcD0HV6A6D6omjqdDOJDO4MQgbwe-3hjjdu454dDWiI_fpSfTcFXbz9RukYeOBk5X0Bj7pcEAT5JsgKXEWyLycwWtayvCs6IFulEMM9EYWI17gR8N856aH8ipmpTJO04kdRdgyGzaF0Ya9_r_e82fe02XkczFSY1hrdoveCNOd6iBjhBm0cUqrKoAjy6r6_2bwR1n0kys9U3AajOuiD8OXCfpbiw"
                if (token != null && token.isNotEmpty()) {
                val authenticatedRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(authenticatedRequest)
            } else {
                chain.proceed(request)
            }
        } else {
            chain.proceed(request)
        }
    }
}