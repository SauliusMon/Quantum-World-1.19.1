package com.saulius.quantum_world.tools;

import net.minecraftforge.energy.EnergyStorage;

public abstract class FEEnergyImpl extends EnergyStorage {

    private final int maxReceiving;
    private final int maxSend;

    public FEEnergyImpl(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.maxReceiving = maxTransfer;
        this.maxSend = maxTransfer;
    }

    public FEEnergyImpl(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.maxReceiving = maxReceive;
        this.maxSend = maxExtract;
    }

    @Override
    public int receiveEnergy(int receivingEnergy, boolean simulate) {
        return super.receiveEnergy(receivingEnergy, simulate);
    }

    @Override
    public int extractEnergy(int extractEnergy, boolean simulate) {
        return super.extractEnergy(extractEnergy, simulate);
    }

    @Override
    public int getEnergyStored() {
        return super.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored() {
        return super.getMaxEnergyStored();
    }

    @Override
    public boolean canExtract() {
        return super.canExtract();
    }

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }

    public void setEnergy (int energy) {
        this.energy = energy;
    }

    public int getMaxReceiving() { return maxReceiving; }

    public int getMaxSend () { return maxSend; }

    public abstract void onEnergyChange();
}
