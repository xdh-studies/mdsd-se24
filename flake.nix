{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/master";
    utils.url = "github:numtide/flake-utils";
    utils.inputs.nixpkgs.follows = "nixpkgs";
  };

  outputs = { self, nixpkgs, ... }@inputs: inputs.utils.lib.eachSystem [
    "x86_64-linux" "i686-linux" "aarch64-linux" "x86_64-darwin"
  ] (system: let pkgs = import nixpkgs {
                   inherit system;
                   overlays = [];
                   config.allowUnfree = true;
                 };
             in {
               devShell = pkgs.mkShell rec {
                 packages = with pkgs; [
                 	jdk21
                 ];
               };
             });
}
