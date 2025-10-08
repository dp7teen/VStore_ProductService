package com.dp.vstore_productservice.security;

import java.io.Serializable;

public record UserPrincipal(String id, String email) implements Serializable {
}
